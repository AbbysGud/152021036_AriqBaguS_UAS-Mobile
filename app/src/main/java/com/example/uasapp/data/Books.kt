package com.example.uasapp.data

import com.google.gson.annotations.SerializedName

data class Books(

	@field:SerializedName("Books")
	val books: List<BooksItem?>? = null
)

data class BooksItem(

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("author")
	val author: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("publish_date")
	val publishDate: String? = null
)
