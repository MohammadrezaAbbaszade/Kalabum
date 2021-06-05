package com.noavaranpishroensheab.kalabum

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.noavaranpishroensheab.kalabum.viewmodels.LoginViewModel
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    lateinit var loginViewModel: LoginViewModel
    var inputTypeChanged = false
    var token: String = ""
    companion object {
        fun newIntent(context: Context): Intent {
            val intent = Intent(context, LoginActivity::class.java)
            return intent
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        password_edit_txt.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD;
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)

        login_btn.setOnClickListener {


            loginViewModel.login(
                SharePreferenceData.getToken(this).toString(),
                Login(

                    phone_number_edit_txt.text.trim().toString(),
                    password_edit_txt.text.trim().toString()
                )
            )

            loginViewModel.mLogin?.observe(this, Observer<LoginResponse> {
                if (it != null) {
                    if (it.success) {
                        SharePreferenceData.setToken(this, "bearer " + it.data.token)
                        Toast.makeText(this, "ورود شما با موفقیت انجام شد", Toast.LENGTH_LONG).show()
                        val intent = MainActivity.newIntent(this)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    }
                } else {
                    Toast.makeText(this, "خطای برقراری ارتباط!", Toast.LENGTH_LONG).show()
                }

            })

        }


    }

}