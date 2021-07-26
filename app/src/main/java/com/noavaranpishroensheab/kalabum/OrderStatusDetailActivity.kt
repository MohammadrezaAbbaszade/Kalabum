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
import com.noavaranpishroensheab.kalabum.response.*
import com.noavaranpishroensheab.kalabum.response.Item
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

                orderStatusDetailViewModel.getRequestDetail(
                    SharePreferenceData.getToken(this).toString(), id
                )
            }

        }

        orderStatusDetailViewModel.mInvoiceDetailInfo.observe(this, Observer {

            if (it != null) {
                order_status_detail_progressbar.visibility = View.GONE
                order_status_detail_title.visibility = View.VISIBLE
                if (it.data.invoice.valid) {
                    ready_factor_info.visibility = View.VISIBLE
                    expired_normal_text.text = it.data.invoice.validUntil
                    activity_order_detail_total_price.text = it.data.invoice.totalPrice.toString()
                    activity_order_detail_expire_date.text = it.data.invoice.validUntil
                    ready_factor_bottom_view.visibility = View.VISIBLE
                } else {
                    end_factor_info.visibility = View.VISIBLE
                    activity_order_detail_end_factor_date.text = it.data.invoice.validUntil
                    end_factor_bottom_view.visibility = View.VISIBLE
                }


                order_status_detail_main_title_number.text = it.data.invoice.id.toString()
                order_status_detail_main_title.text = "پیش فاکتور"

                order_status_recycler.adapter = InvoiceAdapter(this, it.data.invoice.items)
            }


        })


        orderStatusDetailViewModel.mFactorDetailInfo.observe(this, Observer {

            if (it != null) {
                order_status_detail_progressbar.visibility = View.GONE
                order_status_detail_title.visibility = View.VISIBLE
                order_status_detail_main_title_number.text = it.data.invoice.id.toString()
                order_status_detail_main_title.text = "فاکتور"
                order_status_recycler.adapter = InvoiceAdapter(this, null, it.data.invoice.items)
            }


        })



        orderStatusDetailViewModel.mRequestDetailInfo.observe(this, Observer {

            if (it != null) {
                order_status_detail_progressbar.visibility = View.GONE
                order_status_detail_title.visibility = View.VISIBLE
                comming_soon_factor_bottom_view.visibility = View.VISIBLE
                order_status_detail_main_title_number.text = it.data.invoice.id.toString()
                order_status_detail_main_title.text = "درخواست"
                order_status_recycler.adapter = RequestAdapter(this, it.data.invoice.items)
            }


        })


    }

    class RequestAdapter(
        val context: Context,
        val requestDetailItem: List<RequestDetailItem>
    ) : RecyclerView.Adapter<RequestAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(context)
                .inflate(R.layout.coming_soon_factor_item, parent, false)
            return ViewHolder(view, context)
        }

        override fun getItemCount(): Int {
            return requestDetailItem.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(requestDetailItem.get(position))
            holder.itemView.setOnClickListener {

            }
        }


        class ViewHolder(private val view: View, val context: Context) :
            RecyclerView.ViewHolder(view) {


            fun bind(item: RequestDetailItem) {
                with(view) {
                    coming_soon_factor_item_title_txt.text = item.product.name
                    coming_soon_factor_item_recycler.adapter =
                        RequestOptionsAdapter(context, item.product.options)
                }

            }


        }


    }


    class RequestOptionsAdapter(
        val context: Context,
        var requestDetailOption: List<RequestDetailOption>
    ) : RecyclerView.Adapter<RequestOptionsAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(context)
                .inflate(R.layout.coming_soon_factor_options_item, parent, false)
            return ViewHolder(view, context)
        }

        override fun getItemCount(): Int {
            return requestDetailOption.size
        }


        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(requestDetailOption.get(position))
            holder.itemView.setOnClickListener {

            }
        }


        class ViewHolder(private val view: View, val context: Context) :
            RecyclerView.ViewHolder(view) {

            fun bind(item: RequestDetailOption) {
                with(view) {
                    coming_soon_factor_item_option_name.text = item.name
                    coming_soon_factor_item_option_order.text = item.order.toString()
                }


            }


        }


    }


    class InvoiceAdapter(
        val context: Context,
        var invoiceList: List<InvoiceDetailItem>? = null,
        var factorList: List<Item>? = null
    ) : RecyclerView.Adapter<InvoiceAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(context)
                .inflate(R.layout.invoice_detail_item, parent, false)
            return ViewHolder(view, context)
        }

        override fun getItemCount(): Int {
            if (invoiceList != null) {
                return invoiceList!!.size
            } else {
                return factorList!!.size
            }
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            if (invoiceList != null) {
                holder.bind(invoiceList!!.get(position), invoiceList!!)
            } else {
                holder.bind(factorList!!.get(position), factorList!!)
            }

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
                            item.product.options,
                            null
                        )
                }


            }

            fun bind(item: Item, invoiceList: List<Item>) {
                with(view) {
                    invoice_detail_item_product_name.text = item.product.name
                    invoice_detail_item_note.text = item.notes.toString()
                    invoice_detail_item_price_per_unit.text = item.pricePerUnit.toString()
                    invoice_detail_item_total_price.text =
                        (item.count * item.pricePerUnit).toString()
                    invoice_detail_item_recycler.adapter =
                        InvoiceOptionsAdapter(
                            context,
                            item.count,
                            item.unit,
                            null,
                            item.product.options
                        )
                }


            }

        }


    }


    class InvoiceOptionsAdapter(
        val context: Context,
        var count: Int,
        var unit: String,
        var invoiceOptions: List<InvoiceDetailOption>? = null,
        var factorOptions: List<Option>? = null
    ) : RecyclerView.Adapter<InvoiceOptionsAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(context)
                .inflate(R.layout.invoice_detail_options_item, parent, false)
            return ViewHolder(view, context)
        }

        override fun getItemCount(): Int {
            if (invoiceOptions != null) {
                return invoiceOptions?.size?.plus(1)!!
            } else {
                return factorOptions?.size?.plus(1)!!
            }
        }

        override fun getItemViewType(position: Int): Int {
            return when (position) {
                0 -> {
                    1
                }

                else -> {
                    2
                }
            }
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {

            when (holder.itemViewType) {
                1 -> {
                    holder.bind(count, unit)
                }
                2 -> {
                    if (factorOptions != null && position - 1 < factorOptions?.size!!) {
                        holder.bind(factorOptions?.get(position - 1)!!)
                    } else if (invoiceOptions != null && position - 1 < invoiceOptions?.size!!) {
                        holder.bind(invoiceOptions?.get(position - 1)!!)
                    }

                }
            }
            holder.itemView.setOnClickListener {

            }
        }


        class ViewHolder(private val view: View, val context: Context) :
            RecyclerView.ViewHolder(view) {

            fun bind(item: InvoiceDetailOption) {
                with(view) {
                    invoice_detail_item_option_name.text = item.name
                    invoice_detail_item_option_order.text = item.order.toString()
                }


            }

            fun bind(item: Option) {
                with(view) {
                    invoice_detail_item_option_name.text = item.name
                    invoice_detail_item_option_order.text = item.order.toString()
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