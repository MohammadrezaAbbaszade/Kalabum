package com.noavaranpishroensheab.kalabum

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_order_status_detail.*
import kotlinx.android.synthetic.main.toolbar.*

class OrderStatusDetailActivity : AppCompatActivity() {

    companion object {
        fun newIntent(context: Context, type: Int): Intent {
            val intent = Intent(context, OrderStatusDetailActivity::class.java)
            intent.putExtra("type", type)
            return intent
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_status_detail)
        val type = intent.getIntExtra("type", 1)
        toolbar_back.visibility = View.VISIBLE
        toolbar_menu.visibility = View.GONE
        toolbar_back.setOnClickListener {
            finish()
        }
        toolbar_sub_back.setOnClickListener {
            finish()
        }
        when (type) {
            1 -> {
                ready_factor_info.visibility=View.VISIBLE
                ready_factor_item_view.visibility = View.VISIBLE
                ready_factor_bottom_view.visibility = View.VISIBLE
            }

            2->{
                coming_soon_factor_item_view.visibility=View.VISIBLE
                comming_soon_factor_bottom_view.visibility=View.VISIBLE
            }

        }

    }


}