package com.noavaranpishroensheab.kalabum.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.noavaranpishroensheab.kalabum.CategoryResponse
import com.noavaranpishroensheab.kalabum.SubCategoriesResponse
import com.noavaranpishroensheab.kalabum.response.FactorListResponse
import com.noavaranpishroensheab.kalabum.service.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FactorListViewModel (application: Application) : AndroidViewModel(application) {

    lateinit var mFactors: MutableLiveData<FactorListResponse>



    val mApi by lazy {
        RetrofitInstance.create()
    }


    fun getFactorList(token:String):MutableLiveData<FactorListResponse>{
        mFactors= MutableLiveData()

        mApi.getFactors(token).enqueue(object : Callback<FactorListResponse> {
            override fun onResponse(
                call: Call<FactorListResponse>,
                response: Response<FactorListResponse>
            ) {
                if (response.isSuccessful) {
                    mFactors.value = response.body()!!

                } else {
                    mFactors.value = null
                    call.cancel()
                }
            }
            override fun onFailure(call: Call<FactorListResponse>, t: Throwable) {
                mFactors.value = null
                call.cancel()
            }

        })

        return mFactors
    }






}


