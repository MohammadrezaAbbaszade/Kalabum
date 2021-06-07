package com.noavaranpishroensheab.kalabum


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.category_items.view.*


class CategoryAdapter(val context: Context, var categories: List<Categories>) :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.category_items, parent, false)
        return ViewHolder(view, context)
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(categories.get(position))
        holder.itemView.pipes_and_faucets.setOnClickListener {


            var expanded = categories.get(position).isExapnded

            categories.get(position).isExapnded = !expanded

            // Notify the adapter that item has changed
            notifyItemChanged(position);


        }
    }


    class ViewHolder(private val view: View, val context: Context) : RecyclerView.ViewHolder(view) {

        fun bind(int: Int) {
            view.setOnClickListener {


            }
        }

        fun bind(categories: Categories) {
            with(view){
                val expanded: Boolean = categories.isExapnded
                // Set the visibility based on state
                // Set the visibility based on state
                category_sub_root_view.setVisibility(if (expanded) View.VISIBLE else View.GONE)
            }


        }


    }


}