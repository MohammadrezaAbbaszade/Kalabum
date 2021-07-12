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
import com.noavaranpishroensheab.kalabum.response.InvoiceDetailProduct
import com.noavaranpishroensheab.kalabum.response.InvoiceListDataX
import com.noavaranpishroensheab.kalabum.viewmodels.InvoiceListViewModel
import com.noavaranpishroensheab.kalabum.viewmodels.OrderStatusDetailViewModel
import kotlinx.android.synthetic.main.activity_order_status_detail.*
import kotlinx.android.synthetic.main.coming_soon_factor_item.view.*
import kotlinx.android.synthetic.main.coming_soon_factor_options_item.view.*
import kotlinx.android.synthetic.main.invoice_detail_item.view.*
import kotlinx.android.synthetic.main.invoice_detail_options_item.view.*
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
        order_status_detail_progressbar.visibility = View.VISIBLE
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
                end_factor_bottom_view.visibility = View.VISIBLE
            }

        }

        orderStatusDetailViewModel.mInvoiceDetailInfo.observe(this, Observer {

            if (it != null) {
                order_status_detail_progressbar.visibility = View.GONE
                order_status_detail_title.visibility=View.VISIBLE
                ready_factor_bottom_view.visibility = View.VISIBLE
                ready_factor_info.visibility = View.VISIBLE

                expired_normal_text.text = it.data.invoice.validUntil
                order_status_detail_main_title_number.text = it.data.invoice.id.toString()
                order_status_detail_main_title.text = "پیش فاکتور"
                order_status_recycler.adapter = InvoiceAdapter(this, it.data.invoice.items)
            }


        })


        orderStatusDetailViewModel.mFactorDetailInfo.observe(this, Observer {

            if (it != null) {
                order_status_detail_progressbar.visibility = View.GONE
                comming_soon_factor_bottom_view.visibility = View.VISIBLE
            }


        })

    }


    class InvoiceAdapter(
        val context: Context,
        var invoiceList: List<InvoiceDetailItem>
    ) : RecyclerView.Adapter<InvoiceAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(context)
                .inflate(R.layout.invoice_detail_item, parent, false)
            return ViewHolder(view, context)
        }

        override fun getItemCount(): Int {
            return invoiceList.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(invoiceList.get(position), invoiceList)
            holder.itemView.setOnClickListener {

            }
        }


        class ViewHolder(private val view: View, val context: Context) :
            RecyclerView.ViewHolder(view) {


            fun bind(item: InvoiceDetailItem, invoiceList: List<InvoiceDetailItem>) {
                with(view) {
                    invoice_detail_item_product_name.text = item.product.name
                    invoice_detail_item_note.text = item.notes
                    invoice_detail_item_price_per_unit.text = item.pricePerUnit.toString()
                    invoice_detail_item_total_price.text =
                        (item.count * item.pricePerUnit).toString()
                    invoice_detail_item_recycler.adapter =
                        InvoiceOptionsAdapter(
                            context,
                            item.count,
                            item.unit,
                            item.options
                        )
                }


            }


        }


    }


    class InvoiceOptionsAdapter(
        val context: Context,
        var count: Int,
        var unit: String,
        var options: List<Any>

    ) : RecyclerView.Adapter<InvoiceOptionsAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(context)
                .inflate(R.layout.invoice_detail_options_item, parent, false)
            return ViewHolder(view, context)
        }

        override fun getItemCount(): Int {
            if (options.isNotEmpty()) {
                return options.size
            } else {
                return 1
            }
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            if (options.isNotEmpty()) {
                holder.bind(options.get(position))
            } else {
                holder.bind(count, unit)
            }

            holder.itemView.setOnClickListener {

            }
        }


        class ViewHolder(private val view: View, val context: Context) :
            RecyclerView.ViewHolder(view) {


            fun bind(item: Any) {
                with(view) {

                }


            }

            fun bind(count: Int, unit: String) {
                with(view) {
                    if (!unit.equals("")) {
                        invoice_detail_item_option_name.text = unit
                    } else {
                        invoice_detail_item_option_name.text = "-"
                    }
                    invoice_detail_item_option_order.text = count.toString()
                }


            }

        }


    }

}