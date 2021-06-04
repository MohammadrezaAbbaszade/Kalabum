package com.noavaranpishroensheab.kalabum.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.noavaranpishroensheab.kalabum.Login
import com.noavaranpishroensheab.kalabum.service.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    lateinit var mLogin: MutableLiveData<Login>


    val mApi by lazy {
        RetrofitInstance.create()
    }

    fun login(token: String, login: Login): MutableLiveData<Login> {
        mLogin = MutableLiveData()
        mApi.login(token, login).enqueue(object : Callback<Login> {
            override fun onResponse(call: Call<Login>, response: Response<Login>) {
                if (response.isSuccessful) {
                    Log.e("dkfsjf", "onFailure: " + "/")
                    mLogin.setValue(response.body()!!)
                } else {
                    mLogin.setValue(null)
                    call.cancel()
                }
            }

            override fun onFailure(call: Call<Login>, t: Throwable) {
                Log.e("dkfsjf", "onFailure: " + "/" + t)
                mLogin.setValue(null)
                call.cancel()
            }

        })
        return mLogin
    }


}