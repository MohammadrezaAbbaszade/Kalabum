package com.noavaranpishroensheab.kalabum

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_accept_factor.*
import kotlinx.android.synthetic.main.toolbar.*

class AcceptFactorActivity  : AppCompatActivity() {

    companion object {
        fun newIntent(context: Context): Intent {
            val intent = Intent(context, AcceptFactorActivity::class.java)
            return intent
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accept_factor)
        toolbar_back.visibility = View.VISIBLE
        toolbar_menu.visibility = View.GONE
        toolbar_back.setOnClickListener {
            finish()
        }
        toolbar_sub_back.setOnClickListener {
            finish()
        }
        accept_factor_pay_all.setOnClickListener {
            val intent = FinalPaymentActivity.newIntent(this)
            startActivity(intent)
        }

    }

}