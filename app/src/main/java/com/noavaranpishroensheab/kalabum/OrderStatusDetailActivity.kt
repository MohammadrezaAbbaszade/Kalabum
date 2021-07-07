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
import com.noavaranpishroensheab.kalabum.response.InvoiceDetailOption
import com.noavaranpishroensheab.kalabum.response.InvoiceListDataX
import com.noavaranpishroensheab.kalabum.viewmodels.InvoiceListViewModel
import com.noavaranpishroensheab.kalabum.viewmodels.OrderStatusDetailViewModel
import kotlinx.android.synthetic.main.activity_order_status_detail.*
import kotlinx.android.synthetic.main.coming_soon_factor_item.view.*
import kotlinx.android.synthetic.main.coming_soon_factor_options_item.view.*
import kotlinx.android.synthetic.main.invoice_item.view.*
import kotlinx.android.synthetic.main.toolbar.*

class OrderStatusDetailActivity : AppCompatActivity() {
    lateinit var orderStatusDetailViewModel: OrderStatusDetailViewModel

    companion object {
        val TYPE = "type"
        val ID = "id"
        fun newIntent(context: Context, type: Int, id: Int): Intent {
            val intent = Intent(context, OrderStatusDetailActivity::class.java)
            intent.putExtra(TYPE, type)
            intent.putExtra(ID, id)
            return intent
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_status_detail)
        order_status_detail_progressbar.visibility=View.VISIBLE
        orderStatusDetailViewModel =
            ViewModelProviders.of(this).get(OrderStatusDetailViewModel::class.java)
        val type = intent.getIntExtra(TYPE, 1)
        val id = intent.getIntExtra(ID, 0)
        toolbar_back.visibility = View.VISIBLE
        toolbar_menu.visibility = View.GONE
        toolbar_back.setOnClickListener {
            finish()
        }
        toolbar_sub_back.setOnClickListener {
            finish()
        }
        ready_factor_submit_order_btn.setOnClickListener {
            val intent = AcceptFactorActivity.newIntent(this)
            startActivity(intent)

        }
        ready_factor_edit_order_btn.setOnClickListener {
            val intent = EditingOrderActivity.newIntent(this)
            startActivity(intent)
        }

        when (type) {
            1 -> {
                orderStatusDetailViewModel.getFactorDetail(
                    SharePreferenceData.getToken(this).toString(), id
                )
            }

            2 -> {
                orderStatusDetailViewModel.getInvoiceDetail(
                    SharePreferenceData.getToken(this).toString(), id
                )
            }
            3 -> {

                end_factor_info.visibility = View.VISIBLE
                ready_factor_item_view.visibility = View.VISIBLE
                end_factor_bottom_view.visibility = View.VISIBLE
            }

        }

        orderStatusDetailViewModel.mInvoiceDetailInfo.observe(this, Observer {

            if (it != null) {
                order_status_detail_progressbar.visibility=View.GONE
                comming_soon_factor_bottom_view.visibility = View.VISIBLE
                order_status_recycler.adapter = InvoiceAdapter(this, it.data.invoice.items)
            }


        })


        orderStatusDetailViewModel.mFactorDetailInfo.observe(this, Observer {

            if (it != null) {
                ready_factor_info.visibility = View.VISIBLE
                ready_factor_item_view.visibility = View.VISIBLE
                ready_factor_bottom_view.visibility = View.VISIBLE
            }


        })

    }


    class InvoiceAdapter(
        val context: Context,
        var invoiceList: List<InvoiceDetailItem>
    ) : RecyclerView.Adapter<InvoiceAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(context)
                .inflate(R.layout.coming_soon_factor_item, parent, false)
            return ViewHolder(view, context)
        }

        override fun getItemCount(): Int {
            return invoiceList.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(invoiceList.get(position))
            holder.itemView.setOnClickListener {

            }
        }


        class ViewHolder(private val view: View, val context: Context) :
            RecyclerView.ViewHolder(view) {


            fun bind(item: InvoiceDetailItem) {
                with(view) {
                    coming_soon_factor_item_title_txt.text = item.product.name
                    coming_soon_factor_item_recycler.adapter =
                        InvoiceOptionsAdapter(context, item.product.options)
                }


            }


        }


    }


    class InvoiceOptionsAdapter(
        val context: Context,
        var invoiceList: List<InvoiceDetailOption>
    ) : RecyclerView.Adapter<InvoiceOptionsAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(context)
                .inflate(R.layout.coming_soon_factor_options_item, parent, false)
            return ViewHolder(view, context)
        }

        override fun getItemCount(): Int {
            return invoiceList.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(invoiceList.get(position))
            holder.itemView.setOnClickListener {

            }
        }


        class ViewHolder(private val view: View, val context: Context) :
            RecyclerView.ViewHolder(view) {


            fun bind(item: InvoiceDetailOption) {
                with(view) {
                    coming_soon_factor_item_option_name.text = item.name
                    coming_soon_factor_item_option_order.text = item.order.toString()
                }


            }


        }


    }

}