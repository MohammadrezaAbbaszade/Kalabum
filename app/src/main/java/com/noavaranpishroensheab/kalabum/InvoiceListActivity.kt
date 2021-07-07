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
import com.noavaranpishroensheab.kalabum.response.FactorListResponse
import com.noavaranpishroensheab.kalabum.response.InvoiceListDataX
import com.noavaranpishroensheab.kalabum.response.InvoiceListResponse
import com.noavaranpishroensheab.kalabum.viewmodels.FactorListViewModel
import com.noavaranpishroensheab.kalabum.viewmodels.InvoiceListViewModel
import kotlinx.android.synthetic.main.activity_factor_list.*
import kotlinx.android.synthetic.main.activity_invoice_list.*
import kotlinx.android.synthetic.main.invoice_item.view.*
import kotlinx.android.synthetic.main.sub_category_options_item.view.*
import kotlinx.android.synthetic.main.toolbar.*

class InvoiceListActivity : AppCompatActivity() {
    lateinit var invoiceListViewModel: InvoiceListViewModel

    companion object {
        fun newIntent(context: Context): Intent {
            val intent = Intent(context, InvoiceListActivity::class.java)
            return intent
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_invoice_list)
        invoice_list_progressbar.visibility=View.VISIBLE
        invoiceListViewModel = ViewModelProviders.of(this).get(InvoiceListViewModel::class.java)
        toolbar_back.visibility = View.VISIBLE
        toolbar_menu.visibility = View.GONE
        toolbar_back.setOnClickListener {
            finish()
        }
        toolbar_sub_back.setOnClickListener {
            finish()
        }



        invoiceListViewModel.getInvoiceList(SharePreferenceData.getToken(this).toString())


        invoiceListViewModel.mInvoices.observe(this, Observer<InvoiceListResponse> {


            if (it != null) {
                invoice_list_progressbar.visibility=View.GONE
                var invoiceAdapter = InvoiceAdapter(this, it.data.requests.data)
                invoice_list_recycler.adapter = invoiceAdapter
            }


        })

    }


    class InvoiceAdapter(
        val context: Context,
        var invoiceList: List<InvoiceListDataX>
    ) : RecyclerView.Adapter<InvoiceAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(context)
                .inflate(R.layout.invoice_item, parent, false)
            return ViewHolder(view, context)
        }

        override fun getItemCount(): Int {
            return invoiceList.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(invoiceList.get(position))
            holder.itemView.setOnClickListener {
                val intent = OrderStatusDetailActivity.newIntent(context,2, invoiceList.get(position).id)
                context.startActivity(intent)
            }
        }


        class ViewHolder(private val view: View, val context: Context) :
            RecyclerView.ViewHolder(view) {


            fun bind(item: InvoiceListDataX) {
                with(view) {
                    invoice_item_id.text = item.id.toString()

                }


            }


        }


    }


}