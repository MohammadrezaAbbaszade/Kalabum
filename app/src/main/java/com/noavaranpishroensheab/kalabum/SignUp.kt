package com.noavaranpishroensheab.kalabum

import com.google.gson.annotations.SerializedName

data class SignUp(
    @SerializedName("phone_number")
    val phone_number: String,
    @SerializedName("first_name")
    val first_name: String,
    @SerializedName("last_name")
    val last_name: String,
    @SerializedName("password")
    val password: String

)