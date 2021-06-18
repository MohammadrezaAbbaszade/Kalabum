package com.noavaranpishroensheab.kalabum.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.noavaranpishroensheab.kalabum.*
import com.noavaranpishroensheab.kalabum.service.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    var mLogin: MutableLiveData<LoginResponse>? = null
    var mSignUp: MutableLiveData<SignUpResponse>? = null

    val mApi by lazy {
        RetrofitInstance.create()
    }

    fun signUp(token: String, signUp: SignUp): MutableLiveData<SignUpResponse>? {
        mSignUp = MutableLiveData()
        mApi.signUp(token, signUp.phone_number,signUp.first_name,signUp.last_name,signUp.password).enqueue(object : Callback<SignUpResponse> {
            override fun onResponse(
                call: Call<SignUpResponse>,
                response: Response<SignUpResponse>
            ) {
                if (response.isSuccessful) {
                    Log.e("dkfsjf", "onFailure: " + "/")
                    mSignUp?.setValue(response.body()!!)
                } else {
                    mSignUp?.setValue(null)
                    call.cancel()
                }
            }

            override fun onFailure(call: Call<SignUpResponse>, t: Throwable) {
                mSignUp?.setValue(null)
                call.cancel()
            }


        })
        return mSignUp
    }


    fun login(token: String, login: Login): MutableLiveData<LoginResponse>? {
        mLogin = MutableLiveData()
        mApi.login(token, login.phone_number, login.password)
            .enqueue(object : Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    if (response.isSuccessful) {
                        Log.e("dkfsjf", "onFailure: " + "/")
                        mLogin?.setValue(response.body()!!)
                    } else {
                        mLogin?.setValue(null)
                        call.cancel()
                    }

                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    mLogin?.setValue(null)
                    call.cancel()
                }


            })
        return mLogin
    }


}