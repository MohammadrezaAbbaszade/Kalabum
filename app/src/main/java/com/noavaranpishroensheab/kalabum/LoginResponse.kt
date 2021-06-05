package com.noavaranpishroensheab.kalabum

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("data")
    val data: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("locale")
    val locale: String,
    @SerializedName("code")
    val code: Int,
    @SerializedName("success")
    val success: Boolean

    )

data class Data(
    @SerializedName("token")
    val token: String
)