#include "dawn.h"
#include <stdio.h>
#include <string.h>


/**
 * Pipeline callback for when tags are found.
 * @param list
 * @param tag
 * @param user_data
 */
static void insert_tags (const GstTagList * list, const gchar * tag, gpointer user_data) {

    
    gchar *title, *album, *artist;
    gchar *zErrMsg = 0; /* SQL error message */
    gchar sql[512]; /* create oversized buffer */
    
    gst_tag_list_get_string(list, "title", &title);
    gst_tag_list_get_string(list, "album", &album);
    gst_tag_list_get_string(list, "artist", &artist);

    memset(sql, '\0', sizeof(sql));
    sprintf(sql, "INSERT INTO %s VALUES (\"%s\", \"%s\", \"%s\")", TABLE, title, album, artist);
    
    if( sqlite3_exec(database, sql, NULL, NULL, &zErrMsg) != SQLITE_OK ){
            fprintf(stderr, "SQL error: %s\n", zErrMsg);
            sqlite3_free(zErrMsg);
    }
}

/**
 * Dynamically link up pipeline
 * @param dec
 * @param pad
 * @param fakesink
 */
static void on_new_pad (GstElement * dec, GstPad * pad, GstElement * fakesink) {
	GstPad *sinkpad;

	sinkpad = gst_element_get_static_pad (fakesink, "sink");
	if (!gst_pad_is_linked (sinkpad)) {
		if (gst_pad_link (pad, sinkpad) != GST_PAD_LINK_OK)
			g_error ("Failed to link pads!");
	}
	gst_object_unref (sinkpad);
}

/**
 * Scan a single file
 * @param file
 * @return 
 */
int scan_file (gchar * file) {
	GstElement *pipe, *dec, *sink; /* Scanner pipeline */
	GstMessage *msg; /* Scanner message buffer */
        
	sqlite3 *database; /* SQL database */
	gchar *zErrMsg = 0; /* SQL error message */
	gint returnCode; /* SQL return code */

	if (!gst_uri_is_valid (file))
		g_error ("Must be used with a valid file uri.");

	pipe = gst_pipeline_new ("pipeline");

	dec = gst_element_factory_make ("uridecodebin", NULL);
	g_object_set (dec, "uri", file, NULL);
	gst_bin_add (GST_BIN (pipe), dec);

	sink = gst_element_factory_make ("fakesink", NULL);
	gst_bin_add (GST_BIN (pipe), sink);

	g_signal_connect (dec, "pad-added", G_CALLBACK (on_new_pad), sink);

	gst_element_set_state (pipe, GST_STATE_PAUSED);

	while (TRUE) {
		GstTagList *tags = NULL;

		msg = gst_bus_timed_pop_filtered (GST_ELEMENT_BUS (pipe),
				GST_CLOCK_TIME_NONE,
				GST_MESSAGE_ASYNC_DONE | GST_MESSAGE_TAG | GST_MESSAGE_ERROR);

		if (GST_MESSAGE_TYPE (msg) != GST_MESSAGE_TAG) /* error or async_done */
			break;

		gst_message_parse_tag (msg, &tags);
		gst_tag_list_foreach (tags, insert_tags, NULL);
		gst_tag_list_free (tags);

		gst_message_unref (msg);
	};

	if (GST_MESSAGE_TYPE (msg) == GST_MESSAGE_ERROR)
		g_error ("Got error");

	gst_message_unref (msg);
	gst_element_set_state (pipe, GST_STATE_NULL);
	gst_object_unref (pipe);
	return 0;
}

