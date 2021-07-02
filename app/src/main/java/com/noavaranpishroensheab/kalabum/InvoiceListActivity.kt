package com.noavaranpishroensheab.kalabum

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_invoice_list.*
import kotlinx.android.synthetic.main.sub_category_options_item.view.*
import kotlinx.android.synthetic.main.toolbar.*

class InvoiceListActivity : AppCompatActivity() {

    companion object {
        fun newIntent(context: Context): Intent {
            val intent = Intent(context, InvoiceListActivity::class.java)
            return intent
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_invoice_list)
        toolbar_back.visibility = View.VISIBLE
        toolbar_back.setOnClickListener {
            finish()
        }
        toolbar_sub_back.setOnClickListener {
            finish()
        }

        invoice_list_recycler.adapter = InvoiceAdapter(this, 10)


    }


    class InvoiceAdapter(
        val context: Context,
        var item: Int
    ) : RecyclerView.Adapter<InvoiceAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(context)
                .inflate(R.layout.invoice_item, parent, false)
            return ViewHolder(view, context)
        }

        override fun getItemCount(): Int {
            return item
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(position)
        }


        class ViewHolder(private val view: View, val context: Context) :
            RecyclerView.ViewHolder(view) {


            fun bind(item: Int) {
                with(view) {
                }


            }


        }


    }


}