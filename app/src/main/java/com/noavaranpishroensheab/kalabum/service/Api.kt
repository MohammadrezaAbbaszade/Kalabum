package com.noavaranpishroensheab.kalabum.service

import com.noavaranpishroensheab.kalabum.*
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



    @GET("categories/{id}/filters")
    fun subCategoriesOptions(@Header("Authorization") token: String,@Path("id")  productId:Int): Call<SubCategoriesResponse>


    @Headers("Api-Key: service.rYbt2x21jJ8rg4FkUithMcD5H8Kdaf37hYFCCTsQ")
    @GET("/v2/reverse")
    fun getReverse(@Query("lat") lat: Double, @Query("lng") lng: Double): Call<NeshanAddress>
}