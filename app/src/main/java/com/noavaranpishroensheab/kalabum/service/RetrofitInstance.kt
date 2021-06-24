package com.noavaranpishroensheab.kalabum.service

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    val BASE_URL = "http://app.kalabela.ir/api/v1/"
    val MAP_BASE_URL="https://api.neshan.org/"
    lateinit var retrofitInstance: Retrofit
    val okHttpClient = OkHttpClient.Builder()
    val logging = HttpLoggingInterceptor()

    init {
        logging.level = HttpLoggingInterceptor.Level.BODY
        okHttpClient.addInterceptor(logging)
        okHttpClient.addInterceptor(object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val request =
                    chain.request().newBuilder().addHeader("Content-Type", "application/json")
                        .build()
                return chain.proceed(request)
            }
        })
    }

    fun create(): Api {
        retrofitInstance = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient.build())
            .build()
        return retrofitInstance.create(Api::class.java)
    }

    fun getMapRetrofit(): Api {
        retrofitInstance = Retrofit.Builder()
            .baseUrl(MAP_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient.build())
            .build()
        return retrofitInstance.create(Api::class.java)
    }

}