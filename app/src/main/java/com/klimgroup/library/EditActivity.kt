package com.klimgroup.library

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.klimgroup.library.DataBase.BookItems
import com.klimgroup.library.DataBase.Constants
import com.klimgroup.library.DataBase.DbManager
import com.klimgroup.library.databinding.ActivityEditBinding

class EditActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditBinding
    private lateinit var dbManager: DbManager
    private var isEdit = false
    private var id = -1
    private var title = ""
    private var pages = ""
    override fun onDestroy() {
        super.onDestroy()
        dbManager.closeDb()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setActivity()
        binding.button.setOnClickListener { getValues() }
    }

    private fun setActivity(){
        dbManager = DbManager(this)
        dbManager.openDb()
        Constants.apply {
            intent.apply {
                id = getIntExtra(I_ID,-1)
                if (id != -1){
                    isEdit = true
                    title = getStringExtra(I_TITLE).toString()
                    pages = getStringExtra(I_PAGE).toString()
                }
            }
        }

        Log.d("intent","id: $id")
        Log.d("intent","title: $title")
        Log.d("intent","pages: $pages")

        if (isEdit){
            binding.apply {
                tvAction.text = "Edit Book"
                button.text = "edit"
                edTitle.setText(title)
                edPages.setText(pages)
            }
        }
    }

    private fun getValues(){
        val book = BookItems()
        var correct = true
        binding.apply {
            book.title = edTitle.text.toString()
            book.pages = edPages.text.toString()
            if (book.title.isEmpty()){
                correct = false
                edTitle.error = "Fill the field!"
            }
            if (book.pages.isEmpty()){
                correct = false
                edPages.error = "Fill the field!"
            }
            if (correct){
                if (isEdit){
                    book.id = id
                    dbManager.updateBook(book)
                    Toast.makeText(this@EditActivity,"Book edited successfully",Toast.LENGTH_SHORT).show()
                    finish()
                }else{
                    edTitle.setText("")
                    edPages.setText("")
                    Toast.makeText(this@EditActivity,"Book added successfully",Toast.LENGTH_SHORT).show()
                    dbManager.insertBook(book)
                }
            }
        }
    }
}