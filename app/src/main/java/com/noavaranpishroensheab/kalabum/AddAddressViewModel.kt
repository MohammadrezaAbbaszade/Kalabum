package com.noavaranpishroensheab.kalabum

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.noavaranpishroensheab.kalabum.service.RetrofitInstance
import org.neshan.common.model.LatLng
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddAddressViewModel(application: Application) : AndroidViewModel(application) {
    var address: MutableLiveData<NeshanAddress>? = null

    init {
        address = MutableLiveData()
    }

    val mApi by lazy {
        RetrofitInstance.getMapRetrofit()
    }


    fun getAddress(currentLocation: LatLng): MutableLiveData<NeshanAddress>? {
        mApi.getReverse(currentLocation.latitude, currentLocation.longitude).enqueue(object :
            Callback<NeshanAddress> {
            override fun onResponse(call: Call<NeshanAddress>, response: Response<NeshanAddress>) {
                if (response.isSuccessful) {
                    address?.value = response.body()
                } else {
                    address?.value = null
                    call.cancel()
                }
            }

            override fun onFailure(call: Call<NeshanAddress>, t: Throwable) {
                address?.value = null
                call.cancel()
            }

        })

        return address
    }


}