package com.example.uasapp.book

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.uasapp.Fragment
import com.example.uasapp.R
import com.example.uasapp.data.BooksItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class Update : AppCompatActivity() {

    private lateinit var judulText: TextView
    private lateinit var nameText: EditText
    private lateinit var authorText: EditText
    private lateinit var dateSpin: DatePicker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

        judulText = findViewById(R.id.update_txt_judul)
        nameText = findViewById(R.id.update_name)
        authorText = findViewById(R.id.update_author)
        dateSpin = findViewById(R.id.update_date)

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
                            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                            val date = dateFormat.parse(book.publishDate)

                            date?.let {
                                val calendar = Calendar.getInstance()
                                calendar.time = it
                                val year = calendar.get(Calendar.YEAR)
                                val month = calendar.get(Calendar.MONTH)
                                val day = calendar.get(Calendar.DAY_OF_MONTH)
                                dateSpin.init(year, month, day, null)
                            }
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
                id = bookId,
                name = nameText.text.toString(),
                author = authorText.text.toString(),
                publishDate = getDateFromDatePicker(dateSpin)
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

    private fun getDateFromDatePicker(datePicker: DatePicker): String {
        val day = datePicker.dayOfMonth
        val month = datePicker.month
        val year = datePicker.year

        val calendar = Calendar.getInstance()
        calendar.set(year, month, day)

        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return dateFormat.format(calendar.time)
    }
}