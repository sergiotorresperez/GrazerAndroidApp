package com.sergiotorres.grazer.data.network.dto

import com.google.gson.annotations.SerializedName

data class UserListResponse(
    @SerializedName("status") val status: Int,
    @SerializedName("status_desc") val statusDesc: String,
    @SerializedName("data") val data: UsersData
)

data class UserDto(
    @SerializedName("name") val name: String,
    @SerializedName("date_of_birth") val dateOfBirth: Long,
    @SerializedName("profile_image") val profileImage: String
)

data class Meta(
    @SerializedName("item_count") val itemCount: Int,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("current_page") val currentPage: Int,
)

data class UsersData(
    @SerializedName("users") val users: ArrayList<UserDto>,
    @SerializedName("meta") val meta: Meta,
)