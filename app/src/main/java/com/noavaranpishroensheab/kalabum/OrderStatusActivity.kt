package com.noavaranpishroensheab.kalabum

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_order_status.*
import kotlinx.android.synthetic.main.toolbar.*

class OrderStatusActivity : AppCompatActivity() {

    companion object {
        fun newIntent(context: Context): Intent {
            val intent = Intent(context, OrderStatusActivity::class.java)
            return intent
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_status)
        toolbar_back.visibility = View.VISIBLE
        toolbar_menu.visibility = View.GONE
        toolbar_back.setOnClickListener {
            finish()
        }
        toolbar_sub_back.setOnClickListener {
            finish()
        }
        first_order_status.setOnClickListener {
            val intent = OrderStatusDetailActivity.newIntent(this,1)
            startActivity(intent)
        }

        second_order_status.setOnClickListener {
            val intent = OrderStatusDetailActivity.newIntent(this,2)
            startActivity(intent)
        }
        third_order_status.setOnClickListener {
            val intent = OrderStatusDetailActivity.newIntent(this,3)
            startActivity(intent)
        }
    }


}