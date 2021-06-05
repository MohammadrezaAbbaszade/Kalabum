package com.noavaranpishroensheab.kalabum

import com.google.gson.annotations.SerializedName

data class CategoryResponse(
    @SerializedName("data")
    val data: CategoriesData,
    @SerializedName("message")
    val message: String,
    @SerializedName("locale")
    val locale: String,
    @SerializedName("code")
    val code: Int,
    @SerializedName("success")
    val success: Boolean
)

data class CategoriesData(
    @SerializedName("categories")
    val categories: List<Categories>

)

data class Categories(
    @SerializedName("image")
    val image: String,

    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String

)