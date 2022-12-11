package com.klimgroup.library.DataBase

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.klimgroup.library.EditActivity
import com.klimgroup.library.R

class Adapter(
    private val booksList:ArrayList<BookItems>,
    private val actions: Actions,
    private val context: Context
):RecyclerView.Adapter<Adapter.Holder>() {
    class Holder(itemView: View):RecyclerView.ViewHolder(itemView) {
        private val tvTitle:TextView = itemView.findViewById(R.id.tvTitle)
        private val tvPages:TextView = itemView.findViewById(R.id.tvPages)
        val imgDelete:ImageView = itemView.findViewById(R.id.imgDelete)

        @SuppressLint("SetTextI18n")
        fun fill(book:BookItems){
            tvTitle.text = "Title: ${book.title}"
            tvPages.text = "Pages: ${book.pages}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(parent.context)
        return Holder(inflater.inflate(R.layout.rc_book_item,parent,false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val book = booksList[position]
        holder.fill(book)
        holder.imgDelete.setOnClickListener { actions.delete(book.id) }
        holder.itemView.setOnClickListener {
            val i = Intent(context,EditActivity::class.java).apply {
                Constants.apply {
                    putExtra(I_IS_EDIT,true)
                    putExtra(I_ID,book.id)
                    putExtra(I_TITLE,book.title)
                    putExtra(I_PAGE,book.pages)
                }
            }
            Log.d("intent","intent: $i")
            context.startActivity(i)
        }
    }

    override fun getItemCount(): Int = booksList.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateAdapter(newBooksList:ArrayList<BookItems>){
        booksList.clear()
        booksList.addAll(newBooksList)
        notifyDataSetChanged()
    }
}