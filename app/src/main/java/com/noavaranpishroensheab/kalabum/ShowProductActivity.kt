package com.noavaranpishroensheab.kalabum

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.noavaranpishroensheab.kalabum.viewmodels.CategoryViewModel
import com.smarteist.autoimageslider.IndicatorAnimations
import com.smarteist.autoimageslider.SliderViewAdapter
import kotlinx.android.synthetic.main.activity_show_product.*
import kotlinx.android.synthetic.main.sub_category_options_item.view.*
import kotlinx.android.synthetic.main.toolbar.*

class ShowProductActivity : AppCompatActivity() {



    companion object {
        fun newIntent(context: Context, categoryId: Int): Intent {
            val CATEGORY_ID = "categoryId"
            val intent = Intent(context, ShowProductActivity::class.java)
            intent.putExtra(CATEGORY_ID, categoryId)
            return intent
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_product)
        toolbar_back.visibility = View.VISIBLE
        toolbar_search.visibility = View.GONE
        toolbar_menu.visibility = View.GONE
        toolbar_back.setOnClickListener {
            finish()
        }
        toolbar_sub_back.setOnClickListener {
            finish()
        }

        supportFragmentManager.beginTransaction().replace(R.id.activity_show_product_container, ShowProductFragment.newInstance(21))
            .commit()

    }




}