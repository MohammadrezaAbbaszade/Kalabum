package com.noavaranpishroensheab.kalabum.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.noavaranpishroensheab.kalabum.response.FactorListResponse
import com.noavaranpishroensheab.kalabum.response.InvoiceListResponse
import com.noavaranpishroensheab.kalabum.service.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InvoiceListViewModel (application: Application) : AndroidViewModel(application) {

    lateinit var mInvoices: MutableLiveData<InvoiceListResponse>



    val mApi by lazy {
        RetrofitInstance.create()
    }


    fun getInvoiceList(token:String):MutableLiveData<InvoiceListResponse>{
        mInvoices= MutableLiveData()

        mApi.getPreInvoices(token).enqueue(object : Callback<InvoiceListResponse> {
            override fun onResponse(
                call: Call<InvoiceListResponse>,
                response: Response<InvoiceListResponse>
            ) {
                if (response.isSuccessful) {
                    mInvoices.value = response.body()!!

                } else {
                    mInvoices.value = null
                    call.cancel()
                }
            }
            override fun onFailure(call: Call<InvoiceListResponse>, t: Throwable) {
                mInvoices.value = null
                call.cancel()
            }

        })

        return mInvoices
    }






}