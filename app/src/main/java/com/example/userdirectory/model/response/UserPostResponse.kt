package com.example.userdirectory.model.response

import com.google.gson.annotations.SerializedName

data class UserPostResponse(

	@field:SerializedName("UserPostResponse")
	val userPostResponse: List<UserPostResponseItem?>? = null
)

data class UserPostResponseItem(

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("body")
	val body: String? = null,

	@field:SerializedName("userId")
	val userId: Int? = null
)
