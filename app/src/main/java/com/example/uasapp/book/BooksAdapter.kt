package com.example.uasapp.book

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.uasapp.Fragment
import com.example.uasapp.R
import com.example.uasapp.data.BooksItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BooksAdapter(val context: Context, val data: List<BooksItem?>?) : RecyclerView.Adapter<BooksAdapter.MyHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_books, parent, false)
        return MyHolder(v, context)
    }
    override fun getItemCount(): Int = data?.size ?: 0
    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.bind(data?.get(position))
    }
    class MyHolder(itemView: View,  private val context: Context) : RecyclerView.ViewHolder(itemView) {
        fun bind(get: BooksItem?) {
            itemView.findViewById<TextView>(R.id.book_id).text = "${get?.id}"
            itemView.findViewById<TextView>(R.id.book_name).text = get?.name
            itemView.findViewById<TextView>(R.id.book_author).text = get?.author
            itemView.findViewById<TextView>(R.id.book_date).text = get?.publishDate
            val btnEdit = itemView.findViewById<Button>(R.id.book_btn_edit)
            btnEdit.setOnClickListener {
                val intent = Intent(itemView.context, Update::class.java)
                intent.putExtra("bookId", get?.id ?: -1)
                itemView.context.startActivity(intent)
            }
            val btnDelete = itemView.findViewById<Button>(R.id.book_btn_delete)
            btnDelete.setOnClickListener {
                val api = RetrofitConfig.retrofit.create(BookAPI::class.java)
                get?.id?.let { id ->
                    api.deleteBook(id).enqueue(object : Callback<Void> {
                        override fun onResponse(call: Call<Void>, response: Response<Void>) {
                            if (response.isSuccessful) {
                                Toast.makeText(context, "${get?.name} Successfully Deleted", Toast.LENGTH_LONG).show()
                                val intent = Intent(itemView.context, Fragment::class.java)
                                intent.putExtra("TARGET_FRAGMENT", "fCrud")
                                itemView.context.startActivity(intent)
                            } else {
                                Toast.makeText(context, "Failed to delete ${get?.name}", Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onFailure(call: Call<Void>, t: Throwable) {
                            Log.e("API_ERROR", "Request failed: ${t.message}")
                        }
                    })
                }
            }
        }
    }
}