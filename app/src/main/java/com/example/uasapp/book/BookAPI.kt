package com.example.uasapp.book

import com.example.uasapp.data.BooksItem
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface BookAPI {
    @GET("books")
    fun getBooks(): Call<List<BooksItem>>
    @POST("books")
    fun addBook(@Body newBook: BooksItem): Call<BooksItem>
    @GET("books/{id}")
    fun getBookDetails(@Path("id") bookId: Int): Call<BooksItem>
    @PUT("books/{id}")
    fun updateBookDetails(@Path("id") bookId: Int, @Body updatedBook: BooksItem): Call<BooksItem>
    @DELETE("books/{id}")
    fun deleteBook(@Path("id") bookId: Int): Call<Void>
}