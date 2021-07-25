package com.noavaranpishroensheab.kalabum

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.noavaranpishroensheab.kalabum.viewmodels.OrderStatusDetailViewModel
import com.noavaranpishroensheab.kalabum.viewmodels.OrderStatusViewModel
import kotlinx.android.synthetic.main.activity_order_status.*
import kotlinx.android.synthetic.main.toolbar.*

class OrderStatusActivity : AppCompatActivity() {
    lateinit var orderStatusViewModel: OrderStatusViewModel

    companion object {
        fun newIntent(context: Context): Intent {
            val intent = Intent(context, OrderStatusActivity::class.java)
            return intent
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_status)
        orderStatusViewModel =
            ViewModelProviders.of(this).get(OrderStatusViewModel::class.java)
        orderStatusViewModel.getRequests(SharePreferenceData.getToken(this).toString())
        toolbar_back.visibility = View.VISIBLE
        toolbar_menu.visibility = View.GONE
        toolbar_back.setOnClickListener {
            finish()
        }
        toolbar_sub_back.setOnClickListener {
            finish()
        }
        first_order_status.setOnClickListener {
            val intent = OrderStatusDetailActivity.newIntent(this, 1, 2)
            startActivity(intent)
        }

        second_order_status.setOnClickListener {
            val intent = OrderStatusDetailActivity.newIntent(this, 2, 1)
            startActivity(intent)
        }
        third_order_status.setOnClickListener {
            val intent = OrderStatusDetailActivity.newIntent(this, 3, 0)
            startActivity(intent)
        }


        orderStatusViewModel.mRequestList.observe(this, Observer {

            if (it != null) {

            }else{

            }


        })


    }


}