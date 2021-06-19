package com.noavaranpishroensheab.kalabum
import com.google.gson.annotations.SerializedName


data class SubCategoriesResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val `data`: SubCategoriesData,
    @SerializedName("locale")
    val locale: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean
)

data class SubCategoriesData(
    @SerializedName("items")
    val items: List<Item>
)

data class Item(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("order")
    val order: Int,
    @SerializedName("values")
    val values: List<String>
)