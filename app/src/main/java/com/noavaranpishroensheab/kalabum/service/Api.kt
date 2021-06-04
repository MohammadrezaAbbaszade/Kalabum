package com.noavaranpishroensheab.kalabum.service

import com.noavaranpishroensheab.kalabum.Login
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface Api {

    @POST("login")
    fun login(@Header("Authorization") token: String, @Body login: Login): Call<Login>


}