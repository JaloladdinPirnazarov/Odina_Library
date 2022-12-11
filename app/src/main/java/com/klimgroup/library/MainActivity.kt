package com.klimgroup.library

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.klimgroup.library.DataBase.Actions
import com.klimgroup.library.DataBase.Adapter
import com.klimgroup.library.DataBase.Constants
import com.klimgroup.library.DataBase.DbManager
import com.klimgroup.library.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(),Actions {
    private lateinit var binding: ActivityMainBinding
    private lateinit var dbManager: DbManager
    private lateinit var adapter: Adapter

    override fun onDestroy() {
        super.onDestroy()
        dbManager.closeDb()
    }
    override fun onResume() {
        super.onResume()
        updateAdapter()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        binding.btnNewBook.setOnClickListener {
            val i = Intent(this,EditActivity::class.java).putExtra(Constants.I_IS_EDIT,false)
            startActivity(i)
        }
    }

    private fun init(){
        dbManager = DbManager(this)
        dbManager.openDb()
        adapter = Adapter(ArrayList(),this,this)
        binding.rcBooks.layoutManager = LinearLayoutManager(this)
        binding.rcBooks.adapter = adapter
    }

    private fun updateAdapter(){
        val booksList = dbManager.getBooks()
        adapter.updateAdapter(booksList)
        binding.tvEmpty.visibility = if (booksList.size == 0) View.VISIBLE else View.GONE
    }

    override fun delete(id: Int) {
        AlertDialog.Builder(this).apply {
            setIcon(R.drawable.warning)
            setTitle("Warning")
            setMessage("Are you sure want to delete this book?")
            setNegativeButton("No"){_,_->}
            setPositiveButton("Yes"){_,_->
                dbManager.deleteBook(id)
                updateAdapter()
            }
            show()
        }
    }

}