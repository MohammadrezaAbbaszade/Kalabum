package com.noavaranpishroensheab.kalabum.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.noavaranpishroensheab.kalabum.response.FactorDetailResponse
import com.noavaranpishroensheab.kalabum.response.InvoiceDetailResponse
import com.noavaranpishroensheab.kalabum.response.InvoiceListResponse
import com.noavaranpishroensheab.kalabum.service.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderStatusDetailViewModel(application: Application) : AndroidViewModel(application) {
    lateinit var mInvoiceDetailInfo: MutableLiveData<InvoiceDetailResponse>
    lateinit var mFactorDetailInfo: MutableLiveData<FactorDetailResponse>



    val mApi by lazy {
        RetrofitInstance.create()
    }


    fun getInvoiceDetail(token: String, id: Int): MutableLiveData<InvoiceDetailResponse> {

        mInvoiceDetailInfo = MutableLiveData()
        mFactorDetailInfo = MutableLiveData()

        mApi.getPreInvoiceDetail(token, id).enqueue(object : Callback<InvoiceDetailResponse> {

            override fun onResponse(
                call: Call<InvoiceDetailResponse>,
                response: Response<InvoiceDetailResponse>
            ) {
                if (response.isSuccessful) {
                    mInvoiceDetailInfo.value = response.body()!!

                } else {
                    mInvoiceDetailInfo.value = null
                    call.cancel()
                }
            }

            override fun onFailure(call: Call<InvoiceDetailResponse>, t: Throwable) {
                mInvoiceDetailInfo.value = null
                call.cancel()
            }


        })

        return mInvoiceDetailInfo
    }


    fun getFactorDetail(token: String, id: Int): MutableLiveData<FactorDetailResponse> {


        mApi.getInvoiceDetail(token, id).enqueue(object : Callback<FactorDetailResponse> {

            override fun onResponse(
                call: Call<FactorDetailResponse>,
                response: Response<FactorDetailResponse>
            ) {
                if (response.isSuccessful) {
                    mFactorDetailInfo.value = response.body()!!

                } else {
                    mFactorDetailInfo.value = null
                    call.cancel()
                }
            }

            override fun onFailure(call: Call<FactorDetailResponse>, t: Throwable) {
                mFactorDetailInfo.value = null
                call.cancel()
            }


        })

        return mFactorDetailInfo
    }


}