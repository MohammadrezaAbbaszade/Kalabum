package com.noavaranpishroensheab.kalabum

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.noavaranpishroensheab.kalabum.viewmodels.CategoryViewModel
import com.noavaranpishroensheab.kalabum.viewmodels.LoginViewModel
import kotlinx.android.synthetic.main.activity_category.*
import kotlinx.android.synthetic.main.toolbar.*

class CategoryActivity : AppCompatActivity() {
    lateinit var categoryViewModel: CategoryViewModel

    companion object {
        fun newIntent(context: Context): Intent {
            val intent = Intent(context, CategoryActivity::class.java)
            return intent
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)
        categoryViewModel = ViewModelProviders.of(this).get(CategoryViewModel::class.java)
        toolbar_back.visibility = View.VISIBLE
        toolbar_search.visibility = View.VISIBLE
        category_root_view.visibility = View.GONE
        category_progressbar.visibility = View.VISIBLE

        categoryViewModel.getCategories(SharePreferenceData.getToken(this).toString())

        categoryViewModel.mCategories?.observe(this, Observer<CategoryResponse> {

            if (it != null) {
                category_root_view.visibility = View.VISIBLE
                category_progressbar.visibility = View.GONE

                category_recyclerView.adapter = CategoryAdapter(this, it.data.categories)

            } else {
                category_progressbar.visibility = View.GONE
            }

        })

    }


}