package com.noavaranpishroensheab.kalabum.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.noavaranpishroensheab.kalabum.Login
import com.noavaranpishroensheab.kalabum.LoginResponse
import com.noavaranpishroensheab.kalabum.SignUp
import com.noavaranpishroensheab.kalabum.SignUpResponse
import com.noavaranpishroensheab.kalabum.service.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    lateinit var mLogin: MutableLiveData<LoginResponse>
    lateinit var mSignUp: MutableLiveData<SignUpResponse>


    val mApi by lazy {
        RetrofitInstance.create()
    }

    fun signUp(token: String, signUp: SignUp): MutableLiveData<LoginResponse> {
        mSignUp = MutableLiveData()
        mApi.signUp(token, signUp).enqueue(object : Callback<SignUpResponse> {
            override fun onResponse(call: Call<SignUpResponse>, response: Response<SignUpResponse>) {
                if (response.isSuccessful) {
                    Log.e("dkfsjf", "onFailure: " + "/")
                } else {
                    call.cancel()
                }
                mSignUp.setValue(response.body()!!)
            }

            override fun onFailure(call: Call<SignUpResponse>, t: Throwable) {
                mSignUp.setValue(null)
                call.cancel()
            }


        })
        return mLogin
    }


    fun login(token: String, login: Login): MutableLiveData<LoginResponse> {
        mLogin = MutableLiveData()
        mApi.login(token, login.phone_number,login.password).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    Log.e("dkfsjf", "onFailure: " + "/")
                    mLogin.setValue(response.body()!!)
                } else {
                    mLogin.setValue(null)
                    call.cancel()
                }

            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                mLogin.setValue(null)
                call.cancel()
            }


        })
        return mLogin
    }


}