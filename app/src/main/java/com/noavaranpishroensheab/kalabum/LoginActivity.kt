package com.noavaranpishroensheab.kalabum

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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        password_edit_txt.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD;
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)

        login_btn.setOnClickListener {


            loginViewModel.login(
                "bearer "+"2|TwdOKlDq2bGuUgP0vvYE1YxBeNxqNjm25uNbD8Kj",
                Login(phone_number_edit_txt.text.trim().toString(),password_edit_txt.text.trim().toString())
            )

            loginViewModel.mLogin?.observe(this, Observer<Login> {
                if (it != null) {

                } else {

                }

            })

        }





    }

}