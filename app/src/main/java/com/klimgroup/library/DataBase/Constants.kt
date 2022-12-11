package com.klimgroup.library.DataBase

import android.provider.BaseColumns

object Constants {
    const val DATABASE_NAME = "Library.db"
    const val DATABASE_VERSION = 1

    const val TABLE_NAME = "Books"

    const val TITLE = "title"
    const val PAGES = "pages"

    const val CREATE_TABLE = "CREATE TABLE IF NOT EXISTS $TABLE_NAME(" +
            "${BaseColumns._ID} INTEGER PRIMARY KEY, $TITLE STRING, $PAGES STRING)"

    const val DELETE_TABLE = "DELETE TABLE IF EXISTS $TABLE_NAME"

    const val I_IS_EDIT = "is_edit"
    const val I_ID = "is_edit"
    const val I_TITLE = "title"
    const val I_PAGE = "pages"
}