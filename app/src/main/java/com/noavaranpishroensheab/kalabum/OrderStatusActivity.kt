package com.noavaranpishroensheab.kalabum

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.noavaranpishroensheab.kalabum.response.InvoiceDetailItem
import com.noavaranpishroensheab.kalabum.response.Item
import com.noavaranpishroensheab.kalabum.response.RequestListDataX
import com.noavaranpishroensheab.kalabum.viewmodels.OrderStatusDetailViewModel
import com.noavaranpishroensheab.kalabum.viewmodels.OrderStatusViewModel
import kotlinx.android.synthetic.main.activity_order_status.*
import kotlinx.android.synthetic.main.invoice_detail_item.view.*
import kotlinx.android.synthetic.main.request_list_item.view.*
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
//        first_order_status.setOnClickListener {
//            val intent = OrderStatusDetailActivity.newIntent(this, 1, 2)
//            startActivity(intent)
//        }
//
//        second_order_status.setOnClickListener {
//            val intent = OrderStatusDetailActivity.newIntent(this, 2, 1)
//            startActivity(intent)
//        }
//        third_order_status.setOnClickListener {
//            val intent = OrderStatusDetailActivity.newIntent(this, 3, 0)
//            startActivity(intent)
//        }


        orderStatusViewModel.mRequestList.observe(this, Observer {

            if (it != null) {
                activity_order_recycler.adapter = OrderAdapter(this, it.data.requests.data)
            } else {

            }


        })


    }


    class OrderAdapter(
        val context: Context,
        val requestListDataX: List<RequestListDataX>
    ) : RecyclerView.Adapter<OrderAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(context)
                .inflate(R.layout.request_list_item, parent, false)
            return ViewHolder(view, context)
        }

        override fun getItemCount(): Int {
            return requestListDataX.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(requestListDataX.get(position))
            holder.itemView.setOnClickListener {
                val intent = OrderStatusDetailActivity.newIntent(context, 3,requestListDataX.get(position).id)
                context.startActivity(intent)
            }
        }


        class ViewHolder(private val view: View, val context: Context) :
            RecyclerView.ViewHolder(view) {


            fun bind(item: RequestListDataX) {
                with(view) {
                    request_list_item_number.text = item.id.toString()
                    request_list_item_status.text = item.status
                }


            }


        }


    }


}