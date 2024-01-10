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

class Create : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)

        val edtName = findViewById<EditText>(R.id.create_name)
        val edtAuthor = findViewById<EditText>(R.id.create_author)
        val edtPublish = findViewById<DatePicker>(R.id.create_date)
        val today = Calendar.getInstance()
        edtPublish.init(today.get(Calendar.YEAR), today.get(Calendar.MONTH),
            today.get(Calendar.DAY_OF_MONTH), null)

        val btnAdd = findViewById<Button>(R.id.create_btn)
        btnAdd.setOnClickListener {
            val name = edtName.text.toString()
            val author = edtAuthor.text.toString()
            val publish = getDateFromDatePicker(edtPublish)

            if(name.isEmpty()){
                edtName.error = "Book Name's Have to be Filled"
                edtName.requestFocus()
                return@setOnClickListener
            }

            if(author.isEmpty()){
                edtAuthor.error = "Book Author's Have to be Filled"
                edtAuthor.requestFocus()
                return@setOnClickListener
            }

            val Book = BooksItem(
                name = name,
                author = author,
                publishDate = publish
            )

            val api = RetrofitConfig.retrofit.create(BookAPI::class.java)
            api.addBook(Book).enqueue(object : Callback<BooksItem> {
                override fun onResponse(call: Call<BooksItem>, response: Response<BooksItem>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@Create, "Book created successfully", Toast.LENGTH_LONG).show()
                        val intent = Intent(this@Create, Fragment::class.java)
                        intent.putExtra("TARGET_FRAGMENT", "fCrud")
                        this@Create.startActivity(intent)
                    } else {
                        Toast.makeText(this@Create, "Failed to create book", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<BooksItem>, t: Throwable) {
                    Log.e("API_ERROR", "Update request failed: ${t.message}")
                }
            })
        }

        val btnBack = findViewById<TextView>(R.id.create_btn_back)
        btnBack.setOnClickListener {
            val intent = Intent(this, Fragment::class.java)
            intent.putExtra("TARGET_FRAGMENT", "fCrud")
            startActivity(intent)
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