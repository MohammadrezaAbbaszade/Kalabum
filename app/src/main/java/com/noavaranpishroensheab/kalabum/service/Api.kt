package com.noavaranpishroensheab.kalabum.service

import com.noavaranpishroensheab.kalabum.*
import com.noavaranpishroensheab.kalabum.response.*
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
    fun subCategoriesOptions(
        @Header("Authorization") token: String,
        @Path("id") productId: Int
    ): Call<SubCategoriesResponse>


    @Headers("Api-Key: service.rYbt2x21jJ8rg4FkUithMcD5H8Kdaf37hYFCCTsQ")
    @GET("/v2/reverse")
    fun getReverse(@Query("lat") lat: Double, @Query("lng") lng: Double): Call<NeshanAddress>

    @GET("invoices")
    fun getFactors(@Header("Authorization") token: String): Call<FactorListResponse>


    @GET("invoices/{id}")
    fun getInvoiceDetail(
        @Header("Authorization") token: String,
        @Path("id") invoiceId: Int
    ): Call<FactorDetailResponse>

    @GET("pre-invoices")
    fun getPreInvoices(@Header("Authorization") token: String): Call<InvoiceListResponse>


    @GET("pre-invoices/{id}")
    fun getPreInvoiceDetail(
        @Header("Authorization") token: String,
        @Path("id") invoiceId: Int
    ): Call<InvoiceDetailResponse>


    @GET("requests")
    fun getRequests(@Header("Authorization") token: String): Call<RequestListResponse>


    @GET("requests/{id}")
    fun getRequestDetail(@Header("Authorization") token: String, @Path("id") invoiceId: Int):Call<RequestDetailResponse>
}