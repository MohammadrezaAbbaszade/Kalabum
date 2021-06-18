package com.noavaranpishroensheab.kalabum

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.noavaranpishroensheab.kalabum.viewmodels.LoginViewModel
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_signup.*
import kotlinx.android.synthetic.main.toolbar.*

class SignUpActivity : AppCompatActivity() {
    lateinit var loginViewModel: LoginViewModel


    companion object {
        fun newIntent(context: Context): Intent {
            val intent = Intent(context, SignUpActivity::class.java)
            return intent
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        loginViewModel.mSignUp?.observe(this, Observer<SignUpResponse> {
            if (it != null) {
                if (it.success) {
                    SharePreferenceData.setLoginResult(this, true)
                    Toast.makeText(this, "ثبت نام شما با موفقیت انجام شد", Toast.LENGTH_LONG).show()
                    val intent = MainActivity.newIntent(this)
                    startActivity(intent)
                    finish()
                } else {
                    SharePreferenceData.setLoginResult(this, false)
                    Toast.makeText(this, it.data.phone_number.get(0), Toast.LENGTH_LONG).show()
                }
            } else {
                SharePreferenceData.setLoginResult(this, false)
                Toast.makeText(this, "خطای برقراری ارتباط!", Toast.LENGTH_LONG).show()
            }

        })


        sign_up_btn2.setOnClickListener {

            if (signup_first_number_edit_txt.text.isEmpty() || signup_last_number_edit_txt.text.isEmpty() ||
                signup_phone_number_edit_txt.text.isEmpty() ||
                signup_password_edit_txt.text.isEmpty()
            ) {
                if (signup_first_number_edit_txt.text.isEmpty()) {
                    Toast.makeText(this, "فیلد نام الزامی میباشد!", Toast.LENGTH_SHORT).show()
                } else if (signup_last_number_edit_txt.text.isEmpty()) {
                    Toast.makeText(this, "فیلد نام خانوادگی الزامی میباشد!", Toast.LENGTH_SHORT)
                        .show()

                } else if (signup_phone_number_edit_txt.text.isEmpty()) {
                    Toast.makeText(this, " شماره تلفن همراه الزامی میباشد!", Toast.LENGTH_SHORT)
                        .show()

                } else if (signup_password_edit_txt.text.isEmpty()) {
                    Toast.makeText(this, "رمز عبور الزامی میباشد!", Toast.LENGTH_SHORT).show()

                }

            } else if (signup_password_edit_txt.text.trim().length < 8) {
                Toast.makeText(
                    this,
                    "رمز عبور نمیتواند کمتر از هشت کاراکتر باشد!",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (signup_phone_number_edit_txt.text.length < 11) {
                Toast.makeText(
                    this,
                    "رمز عبور نمیتواند کمتر از یازده کاراکتر باشد!",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                loginViewModel.signUp(
                    SharePreferenceData.getToken(this).toString(), SignUp(
                        signup_phone_number_edit_txt.text.trim().toString(),
                        signup_first_number_edit_txt.text.trim().toString(),
                        signup_last_number_edit_txt.text.trim().toString(),
                        signup_password_edit_txt.text.trim().toString()
                    )
                )

            }


        }


    }


}