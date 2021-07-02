package com.noavaranpishroensheab.kalabum

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_factor_list.*
import kotlinx.android.synthetic.main.toolbar.*

class FactorListActivity : AppCompatActivity() {

    companion object {
        fun newIntent(context: Context): Intent {
            val intent = Intent(context, FactorListActivity::class.java)
            return intent
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_factor_list)

        toolbar_back.visibility = View.VISIBLE
        toolbar_back.setOnClickListener {
            finish()
        }
        toolbar_sub_back.setOnClickListener {
            finish()
        }

        factor_list_recycler.adapter = FactorAdapter(this, 10)


    }


    class FactorAdapter(
        val context: Context,
        var item: Int
    ) : RecyclerView.Adapter<FactorAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(context)
                .inflate(R.layout.factor_item, parent, false)
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