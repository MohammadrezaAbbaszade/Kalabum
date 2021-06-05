package com.noavaranpishroensheab.kalabum.service

import com.noavaranpishroensheab.kalabum.CategoryResponse
import com.noavaranpishroensheab.kalabum.LoginResponse
import com.noavaranpishroensheab.kalabum.SignUp
import com.noavaranpishroensheab.kalabum.SignUpResponse
import retrofit2.Call
import retrofit2.http.*

interface Api {

    @POST("login")
    @FormUrlEncoded
    fun login(
        @Header("Authorization") token: String, @Field("phone_number") phoneNumber: String,
        @Field("password") password: String
    ): Call<LoginResponse>


    @POST("register")
    fun signUp(@Header("Authorization") token: String, @Body signUp: SignUp): Call<SignUpResponse>

    @GET("categories/list'")
    fun categories(@Header("Authorization") token: String): Call<CategoryResponse>
}