package com.bassiouny.googlebooks.ui.booklist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bassiouny.googlebooks.R
import com.bassiouny.googlebooks.model.Item
import kotlinx.android.synthetic.main.book_item.view.*

class RepoAdapter(
    private var items: ArrayList<Item>?, private val context: Context
) :
    RecyclerView.Adapter<RepoAdapter.BooksViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BooksViewHolder {
        return BooksViewHolder(
            LayoutInflater.from(context).inflate(R.layout.book_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return items?.size ?: 0
    }

    override fun onBindViewHolder(holder: BooksViewHolder, position: Int) {
        holder.name.text = items?.get(position)?.volumeInfo?.title
    }

    fun updateData(fetchedBooks: java.util.ArrayList<Item>) {
        this.items?.clear()
        this.items?.addAll(fetchedBooks)
        notifyDataSetChanged()
    }

    class BooksViewHolder(val item: View) : RecyclerView.ViewHolder(item) {
        var name: TextView = item.rowName
    }
}

