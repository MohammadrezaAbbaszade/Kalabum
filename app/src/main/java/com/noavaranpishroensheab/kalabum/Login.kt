package com.noavaranpishroensheab.kalabum

import com.google.gson.annotations.SerializedName

data class Login(
    @SerializedName("phone_number")
    val phone_number: String,
    @SerializedName("password")
    val password: String

)