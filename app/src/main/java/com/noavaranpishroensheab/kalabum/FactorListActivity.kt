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
import com.noavaranpishroensheab.kalabum.response.DataX
import com.noavaranpishroensheab.kalabum.response.FactorListResponse
import com.noavaranpishroensheab.kalabum.viewmodels.CategoryViewModel
import com.noavaranpishroensheab.kalabum.viewmodels.FactorListViewModel
import kotlinx.android.synthetic.main.activity_factor_list.*
import kotlinx.android.synthetic.main.factor_item.view.*
import kotlinx.android.synthetic.main.toolbar.*

class FactorListActivity : AppCompatActivity() {
    lateinit var factorListViewModel: FactorListViewModel

    companion object {
        fun newIntent(context: Context): Intent {
            val intent = Intent(context, FactorListActivity::class.java)
            return intent
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_factor_list)
        factorListViewModel = ViewModelProviders.of(this).get(FactorListViewModel::class.java)
        toolbar_menu.visibility = View.GONE
        toolbar_back.visibility = View.VISIBLE
        toolbar_back.setOnClickListener {
            finish()
        }
        toolbar_sub_back.setOnClickListener {
            finish()
        }

        //  factor_list_recycler.adapter = FactorAdapter(this, 20)


        factorListViewModel.getFactorList(SharePreferenceData.getToken(this).toString())


        factorListViewModel.mFactors.observe(this, Observer<FactorListResponse> {

            if (it != null) {
                val factorAdapter = FactorAdapter(this, it.data.requests.data)
                factor_list_recycler.adapter = factorAdapter
            }

        })
    }


    class FactorAdapter(
        val context: Context,
        val factors: List<DataX>
    ) : RecyclerView.Adapter<FactorAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(context)
                .inflate(R.layout.factor_item, parent, false)
            return ViewHolder(view, context)
        }

        override fun getItemCount(): Int {
            return factors.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(factors.get(position))
            holder.itemView.setOnClickListener {
                val intent = OrderStatusDetailActivity.newIntent(context, 1,factors.get(position).id)
                context.startActivity(intent)
            }
        }


        class ViewHolder(private val view: View, val context: Context) :
            RecyclerView.ViewHolder(view) {


            fun bind(item: DataX) {
                with(view) {
                    factor_item_id.text = item.id.toString()
                }


            }


        }


    }


}