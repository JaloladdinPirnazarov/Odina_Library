package com.klimgroup.library.DataBase

import android.annotation.SuppressLint
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DbHelper(context: Context):SQLiteOpenHelper(
    context,Constants.DATABASE_NAME,null,Constants.DATABASE_VERSION
) {
    @SuppressLint("SQLiteString")
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(Constants.CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(Constants.DELETE_TABLE)
        onCreate(db)
    }
}