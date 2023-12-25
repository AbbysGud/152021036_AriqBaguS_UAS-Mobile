package com.example.suitmediainternapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.suitmediainternapp.data.ResultUsers
import com.example.suitmediainternapp.UsersAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

class Third : AppCompatActivity() {

    //region inisiasi variabel global
    private val BASE_URL = "https://reqres.in/api/"
    private lateinit var recyclerView: RecyclerView
    private lateinit var refresh: SwipeRefreshLayout
    private lateinit var emptyText: TextView
    private var full_name: String? = null
    private var nama_1: String? = null
    private var currentPage = 1
    private var isLastPage = false
    private var isLoading = false
    //endregion

    //region Interface API yang digunakan
    interface MyAPI {
        @GET("users")
        fun getUsers(
            @Query("page") page: Int,
            @Query("per_page") perPage: Int
        ): Call<ResultUsers>
    }
    //endregion

    //region fungsi onCreate (ketika halaman dibuat)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)

        //region inisiasi semua objek
        recyclerView = findViewById(R.id.rvUser)
        recyclerView.layoutManager = LinearLayoutManager(this)
        refresh = findViewById(R.id.refresh)
        emptyText = findViewById(R.id.third_txt_empty)
        nama_1 = intent.getStringExtra("txt_nama")
        full_name = intent.getStringExtra("txt_nama2")
        val btn_back = findViewById<ImageView>(R.id.third_btn_back)
        //endregion

        //memanggil fungsi ntuk setup Recycler View (tempat menyimpan user)
        setUpRecyclerView()

        //region ketika user melakukan pull action (refresh)
        refresh.setOnRefreshListener {
            currentPage = 1
            isLastPage = false
            getAllUser()
        }
        //endregion

        //region ketika tombol back diklik
        btn_back.setOnClickListener {
            val pindah = Intent(this, Second::class.java)
            pindah.putExtra("txt_nama2", full_name)
            pindah.putExtra("txt_nama", nama_1)
            startActivity(pindah)
        }
        //endregion

        //memasukkan data User dari API
        getAllUser()
    }
    //endregion

    //region setUpRecyclerView
    private fun setUpRecyclerView() {
        //menginisiasi objek adapter atau setiap objek usernya
        val adapter = UsersAdapter(object : UsersAdapter.UserClickListener {
            override fun onUserClick(firstName: String?, lastName: String?) {
                handleUserClick(firstName, lastName)
            }
        })
        recyclerView.adapter = adapter

        //region menangani ketika user scroll sampe paling bawah API ( load the next page when
        // scrolling to the bottom of the list
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItem = layoutManager.findFirstVisibleItemPosition()

                if (!isLoading && !isLastPage) {
                    if (visibleItemCount + firstVisibleItem >= totalItemCount && firstVisibleItem >= 0) {
                        currentPage++
                        getAllUser()
                    }
                }
            }
        })
        //endregion
    }
    //endregion

    //region fungsi yang menangani ketika user klik salah satu item
    fun handleUserClick(firstName: String?, lastName: String?) {
        full_name = "$firstName $lastName"
        val pindah = Intent(this, Second::class.java)
        pindah.putExtra("txt_nama2", full_name)
        pindah.putExtra("txt_nama", nama_1)
        startActivity(pindah)
    }
    //endregion

    //region fungsi untuk memasukkan data API ke recycle view
    private fun getAllUser() {
        isLoading = true

        //region mengambil data API menggunakan retrofit
        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MyAPI::class.java)
        //endregion

        //region memasukkan data user ke recycle view dari json yang diambil dari API
        api.getUsers(currentPage, 10).enqueue(object : Callback<ResultUsers> {
            //region ketika pada API terdapat data atau tidak kosong
            override fun onResponse(call: Call<ResultUsers>, response: Response<ResultUsers>) {
                refresh.isRefreshing = false
                isLoading = false

                if (response.isSuccessful) {
                    response.body()?.let { resultUsers ->
                        if (currentPage == 1) {
                            // Clear the adapter for the first page
                            (recyclerView.adapter as? UsersAdapter)?.clearData()
                        }

                        val newData = resultUsers.data
                        if (newData != null) {
                            if (newData.isNotEmpty()) {
                                // Add new data to the adapter
                                (recyclerView.adapter as? UsersAdapter)?.addData(newData)
                            } else {
                                // Mark as last page if no more data
                                isLastPage = true
                            }
                        }

                        // Show empty state if no data
                        showEmptyState((recyclerView.adapter as? UsersAdapter)?.itemCount == 0)
                    }
                }
            }
            //endregion

            //region ketika pada API tidak terdapat data atau kosong
            override fun onFailure(call: Call<ResultUsers>, t: Throwable) {
                refresh.isRefreshing = false
                isLoading = false

                showEmptyState(true)
            }
            //endregion
        })
        //endregion
    }

    //region fungsi untuk memberi feedback ke user bahwa tidak ada data user pada API
    private fun showEmptyState(show: Boolean) {
        emptyText.visibility = if (show) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }
    //endregion
}