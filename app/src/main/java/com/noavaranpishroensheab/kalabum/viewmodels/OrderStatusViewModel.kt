package com.noavaranpishroensheab.kalabum.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.noavaranpishroensheab.kalabum.response.FactorDetailResponse
import com.noavaranpishroensheab.kalabum.response.InvoiceDetailResponse
import com.noavaranpishroensheab.kalabum.response.RequestListResponse
import com.noavaranpishroensheab.kalabum.service.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderStatusViewModel(application: Application) : AndroidViewModel(application) {

    lateinit var mRequestList: MutableLiveData<RequestListResponse>


    val mApi by lazy {
        RetrofitInstance.create()
    }


    fun getRequests(token: String): MutableLiveData<RequestListResponse> {
        mRequestList = MutableLiveData()

        mApi.getRequests(token).enqueue(object : Callback<RequestListResponse> {


            override fun onResponse(
                call: Call<RequestListResponse>,
                response: Response<RequestListResponse>
            ) {
                if (response.isSuccessful) {
                    mRequestList.value = response.body()
                } else {
                    mRequestList.value = null
                    call.cancel()
                }
            }

            override fun onFailure(call: Call<RequestListResponse>, t: Throwable) {
                mRequestList.value = null
                call.cancel()
            }

        })


        return mRequestList

    }


}