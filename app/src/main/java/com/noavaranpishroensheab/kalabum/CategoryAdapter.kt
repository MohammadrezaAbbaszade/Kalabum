package com.noavaranpishroensheab.kalabum

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.category_item.view.*

class CategoryAdapter(val context: Context, var categories: List<Categories>) :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.category_item, parent, false)
        return ViewHolder(view, context)
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(categories.get(position))
    }


    class ViewHolder(private val view: View, val context: Context) : RecyclerView.ViewHolder(view) {


        fun bind(categories: Categories) {
            view.setOnClickListener {

            }
            with(view) {
                category_item_txt.text = categories.name

            }
        }


    }


}