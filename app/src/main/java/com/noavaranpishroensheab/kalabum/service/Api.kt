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
    @FormUrlEncoded
    fun signUp(
        @Header("Authorization") token: String,
        @Field("phone_number") phoneNumber: String,
        @Field("first_name") firstName: String,
        @Field("last_name") lastName: String,
        @Field("password") password: String
    ): Call<SignUpResponse>


    @GET("categories/list")
    fun categories(@Header("Authorization") token: String): Call<CategoryResponse>


    @GET("categories/list")
    fun subCategories(
        @Header("Authorization") token: String,
        @QueryMap parentId: HashMap<String, Int>
    ): Call<CategoryResponse>
}