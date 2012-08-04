/* 
 * File:   dawn.h
 * Author: james
 *
 * Created on 04 August 2012, 21:20
 */

#include <sqlite3.h>
#include <gst/gst.h>

#ifndef DAWN_H
#define	DAWN_H

#ifdef	__cplusplus
extern "C" {
#endif

#define DB "dawn_db" /* The name of the database to use */
#define TABLE "music" /* The name of the table in the database */

extern sqlite3 * database;

/* Scanner functions */
int scan_file (gchar * file);


#ifdef	__cplusplus
}
#endif

#endif	/* DAWN_H */

