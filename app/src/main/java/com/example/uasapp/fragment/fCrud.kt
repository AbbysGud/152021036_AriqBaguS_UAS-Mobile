package com.example.uasapp.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.uasapp.book.BookAPI
import com.example.uasapp.book.BooksAdapter
import com.example.uasapp.book.Create
import com.example.uasapp.R
import com.example.uasapp.book.RetrofitConfig
import com.example.uasapp.data.BooksItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class fCrud : Fragment() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_f_crud, container, false)

        recyclerView = view.findViewById(R.id.crud_rv)
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())

        val btnAdd = view.findViewById<Button>(R.id.crud_btn_add)

        btnAdd.setOnClickListener {
            val pindah = Intent(requireActivity(), Create::class.java)
            startActivity(pindah)
        }

        val api = RetrofitConfig.retrofit.create(BookAPI::class.java)

        api.getBooks().enqueue(object : Callback<List<BooksItem>> {
            override fun onResponse(call: Call<List<BooksItem>>, response: Response<List<BooksItem>>) {
                recyclerView.adapter = BooksAdapter(requireActivity(), response.body())
            }

            override fun onFailure(call: Call<List<BooksItem>>, t: Throwable) {
                Log.d("API_ERROR", "Request failed: ${t.message}")
            }
        })

        return view
    }

}