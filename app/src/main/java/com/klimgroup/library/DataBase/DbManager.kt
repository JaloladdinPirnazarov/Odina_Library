package com.klimgroup.library.DataBase

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class DbManager(context: Context) {
    private val dbHelper = DbHelper(context)
    private var db : SQLiteDatabase? = null

    fun openDb(){ db = dbHelper.writableDatabase }
    fun closeDb(){ db?.close() }

    fun insertBook(book:BookItems){
        Constants.apply {
            val values = ContentValues().apply {
                put(TITLE,book.title)
                put(PAGES,book.pages)
            }
            db?.insert(TABLE_NAME,null,values)
        }
    }

    fun updateBook(book:BookItems){
        Constants.apply {
            val values = ContentValues().apply {
                put(TITLE,book.title)
                put(PAGES,book.pages)
            }
            val selection = "${BaseColumns._ID} = ${book.id}"
            db?.update(TABLE_NAME,values,selection,null)
        }
    }

    fun getBooks():ArrayList<BookItems>{
        val booksList = ArrayList<BookItems>()
        val cursor = db?.query(Constants.TABLE_NAME,null,null,null,null,null,null)
        while(cursor?.moveToNext()!!){
            Constants.apply {
                val book = BookItems()
                var index = cursor.getColumnIndex(BaseColumns._ID)
                book.id = cursor.getInt(index)
                index = cursor.getColumnIndex(TITLE)
                book.title = cursor.getString(index)
                index = cursor.getColumnIndex(PAGES)
                book.pages = cursor.getString(index)
                booksList.add(book)
            }
        }
        cursor.close()
        return  booksList
    }

    fun deleteBook(id:Int){
        val selection = "${BaseColumns._ID} = $id"
        db?.delete(Constants.TABLE_NAME,selection,null)
    }
}