/* 
 * File:   main.c
 * Author: james
 *
 * Created on 04 August 2012, 17:35
 */
#include "dawn.h"
#include <stdio.h>
#include <stdlib.h>

sqlite3 *database; /* Define global database variable */

/*
 * 
 */
int main(int argc, char** argv) {

    gint returnCode;
    gchar *zErrMsg;
    gchar sql[512]; /* create oversized buffer */

    /* initialize gstreamer*/
    gst_init(&argc, &argv);
    
    /* Setup sqlite*/
    returnCode = sqlite3_open(DB, &database);
    if( returnCode){
        fprintf( stderr, "Cannot open database: %s:\n", sqlite3_errmsg(database));
        sqlite3_close(database);
        return(EXIT_FAILURE);
    }
    
    memset(sql, '\0', sizeof(sql));
    sprintf(sql, "CREATE TABLE IF NOT EXISTS %s(title text, album text, artist test)", TABLE);
    if( sqlite3_exec(database, sql, NULL, NULL, &zErrMsg) != SQLITE_OK ){
            fprintf(stderr, "SQL error: %s\n", zErrMsg);
            sqlite3_free(zErrMsg);
    }
    
    /* Do things. */
    scan_file(argv[1]);
    
    sqlite3_close(database);
    return (EXIT_SUCCESS);
}

