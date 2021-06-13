package com.noavaranpishroensheab.kalabum

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.noavaranpishroensheab.kalabum.viewmodels.CategoryViewModel
import kotlinx.android.synthetic.main.activity_category.*
import kotlinx.android.synthetic.main.toolbar.*


class CategoryActivity : AppCompatActivity() {
    lateinit var categoryViewModel: CategoryViewModel
    lateinit var queris: HashMap<String, Int>


    companion object {
        private val IS_SUB_CATEGORY = "isSubCategory"
        private val PARENT_ID = "parentId"
        fun newIntent(context: Context, isSubCategory: Boolean, parentId: Int): Intent {
            val intent = Intent(context, CategoryActivity::class.java)
            intent.putExtra(IS_SUB_CATEGORY, isSubCategory)
            intent.putExtra(PARENT_ID, parentId)
            return intent
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)
        categoryViewModel = ViewModelProviders.of(this).get(CategoryViewModel::class.java)
        toolbar_back.visibility = View.VISIBLE
        toolbar_search.visibility = View.VISIBLE
        toolbar_back.setOnClickListener {
            finish()
        }
        toolbar_sub_back.setOnClickListener {
            finish()
        }
        category_root_view.visibility = View.GONE
        category_progressbar.visibility = View.VISIBLE


        if (intent.getBooleanExtra(IS_SUB_CATEGORY, false)) {
            queris = HashMap()
            queris.put("parent_id", intent.getIntExtra(PARENT_ID, 0))

            categoryViewModel.getSubCategories(
                SharePreferenceData.getToken(this).toString(),
                queris
            )
        } else {
            categoryViewModel.getCategories(SharePreferenceData.getToken(this).toString())
        }
        categoryViewModel.mCategories?.observe(this, Observer<CategoryResponse> {

            if (it != null) {
                category_root_view.visibility = View.VISIBLE
                category_progressbar.visibility = View.GONE
                var categoryAdapter = CategoryAdapter(this, true, it.data.categories)
                categoryAdapter.setOnClickListener(object : CategoryAdapter.SubCategoryClicked {
                    override fun clicked(parentId: Int) {
                        val intent = newIntent(this@CategoryActivity, true, parentId)
                        startActivity(intent)
                    }

                })
                category_recyclerView.adapter = categoryAdapter


            } else {
                category_progressbar.visibility = View.GONE
            }

        })



        categoryViewModel.mSubCategories?.observe(this, Observer<CategoryResponse> {

            if (it != null) {
                category_root_view.visibility = View.VISIBLE
                category_progressbar.visibility = View.GONE
                var categoryAdapter = CategoryAdapter(this, false, it.data.categories)

                category_recyclerView.adapter = categoryAdapter

            } else {
                category_progressbar.visibility = View.GONE
            }

        })

    }


}