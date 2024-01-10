package com.example.uasapp.book

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.uasapp.Fragment
import com.example.uasapp.R
import com.example.uasapp.data.BooksItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Update : AppCompatActivity() {

    private val BASE_URL = "http://10.0.2.2:8000/api/"
    private lateinit var judulText: TextView
    private lateinit var nameText: EditText
    private lateinit var authorText: EditText
    private lateinit var dateText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

        judulText = findViewById(R.id.update_txt_judul)
        nameText = findViewById(R.id.update_name)
        authorText = findViewById(R.id.update_author)
        dateText = findViewById(R.id.update_publish)

        val btnBack = findViewById<TextView>(R.id.update_btn_back)
        btnBack.setOnClickListener {
            val intent = Intent(this, Fragment::class.java)
            intent.putExtra("TARGET_FRAGMENT", "fCrud")
            this.startActivity(intent)
        }

        val api = RetrofitConfig.retrofit.create(BookAPI::class.java)
        val bookId = intent.getIntExtra("bookId", -1)
        if (bookId != -1) {
            api.getBookDetails(bookId).enqueue(object : Callback<BooksItem> {
                override fun onResponse(call: Call<BooksItem>, response: Response<BooksItem>) {
                    if (response.isSuccessful) {
                        response.body()?.let { book ->
                            judulText.text = "Edit ${book.name}"
                            nameText.setText(book.name)
                            authorText.setText(book.author)
                            dateText.setText(book.publishDate)
                        }
                    }
                }

                override fun onFailure(call: Call<BooksItem>, t: Throwable) {
                    Log.e("API_ERROR", "Request failed: ${t.message}")
                }
            })
        } else {
            Toast.makeText(this, "THERES NO BOOK WITH THIS ID", Toast.LENGTH_SHORT).show()
            finish()
        }

        val btnUpdate = findViewById<Button>(R.id.update_btn)
        btnUpdate.setOnClickListener {
            val updatedBook = BooksItem(
                id = bookId, // Use the bookId obtained from the intent
                name = nameText.text.toString(),
                author = authorText.text.toString(),
                publishDate = dateText.text.toString()
            )

            api.updateBookDetails(bookId, updatedBook).enqueue(object : Callback<BooksItem> {
                override fun onResponse(call: Call<BooksItem>, response: Response<BooksItem>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@Update, "Book updated successfully", Toast.LENGTH_LONG).show()
                        val intent = Intent(this@Update, Fragment::class.java)
                        intent.putExtra("TARGET_FRAGMENT", "fCrud")
                        this@Update.startActivity(intent)
                    } else {
                        Toast.makeText(this@Update, "Failed to update book", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<BooksItem>, t: Throwable) {
                    Log.e("API_ERROR", "Update request failed: ${t.message}")
                }
            })
        }


    }
}