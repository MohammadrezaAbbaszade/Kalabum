package com.noavaranpishroensheab.kalabum.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.noavaranpishroensheab.kalabum.CategoryResponse
import com.noavaranpishroensheab.kalabum.LoginResponse
import com.noavaranpishroensheab.kalabum.SignUpResponse
import com.noavaranpishroensheab.kalabum.service.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryViewModel(application: Application) : AndroidViewModel(application) {

    var mCategories: MutableLiveData<CategoryResponse>? = null
    var mSubCategories: MutableLiveData<CategoryResponse>? = null

    val mApi by lazy {
        RetrofitInstance.create()
    }

    fun getCategories(token: String): MutableLiveData<CategoryResponse> {
        mCategories = MutableLiveData()

        mApi.categories(token).enqueue(object : Callback<CategoryResponse> {
            override fun onResponse(
                call: Call<CategoryResponse>,
                response: Response<CategoryResponse>
            ) {
                if (response.isSuccessful) {
                    mCategories?.value = response.body()!!

                } else {
                    mCategories?.value = null
                    call.cancel()
                }
            }

            override fun onFailure(call: Call<CategoryResponse>, t: Throwable) {
                mCategories?.value = null
                call.cancel()
            }


        })

        return MutableLiveData()
    }


    fun getSubCategories(
        token: String,
        queries: HashMap<String, Int>
    ): MutableLiveData<CategoryResponse> {
        mSubCategories = MutableLiveData()

        mApi.subCategories(token, queries).enqueue(object : Callback<CategoryResponse> {
            override fun onResponse(
                call: Call<CategoryResponse>,
                response: Response<CategoryResponse>
            ) {
                if (response.isSuccessful) {
                    mSubCategories?.value = response.body()!!

                } else {
                    mSubCategories?.value = null
                    call.cancel()
                }
            }

            override fun onFailure(call: Call<CategoryResponse>, t: Throwable) {
                mSubCategories?.value = null
                call.cancel()
            }


        })


        return MutableLiveData()
    }


}