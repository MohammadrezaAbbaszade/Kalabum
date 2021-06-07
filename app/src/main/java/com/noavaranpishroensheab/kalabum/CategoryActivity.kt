package com.noavaranpishroensheab.kalabum

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.SimpleItemAnimator
import com.noavaranpishroensheab.kalabum.viewmodels.CategoryViewModel
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
        toolbar_back.setOnClickListener {
            finish()
        }
//        category_root_view.visibility = View.GONE
//        category_progressbar.visibility = View.VISIBLE

        // categoryViewModel.getCategories(SharePreferenceData.getToken(this).toString())
        (category_recyclerView.getItemAnimator() as SimpleItemAnimator).supportsChangeAnimations =
            false
        var list = mutableListOf<Categories>()
        list.add(Categories(false, "fdfdf", 5, "fsdfsfsd"))
        list.add(Categories(false, "fdfdf", 5, "fsdfsfsd"))
        list.add(Categories(false, "fdfdf", 5, "fsdfsfsd"))
        list.add(Categories(false, "fdfdf", 5, "fsdfsfsd"))
        list.add(Categories(false, "fdfdf", 5, "fsdfsfsd"))
        list.add(Categories(false, "fdfdf", 5, "fsdfsfsd"))
        list.add(Categories(false, "fdfdf", 5, "fsdfsfsd"))
        list.add(Categories(false, "fdfdf", 5, "fsdfsfsd"))
        list.add(Categories(false, "fdfdf", 5, "fsdfsfsd"))
        list.add(Categories(false, "fdfdf", 5, "fsdfsfsd"))
        list.add(Categories(false, "fdfdf", 5, "fsdfsfsd"))
        list.add(Categories(false, "fdfdf", 5, "fsdfsfsd"))
        list.add(Categories(false, "fdfdf", 5, "fsdfsfsd"))
        category_recyclerView.adapter = CategoryAdapter(this, list)


//        categoryViewModel.mCategories?.observe(this, Observer<CategoryResponse> {
//
//            if (it != null) {
//                category_root_view.visibility = View.VISIBLE
//                category_progressbar.visibility = View.GONE
//
//                category_recyclerView.adapter = CategoryAdapter(this, it.data.categories)
//
//            } else {
//                category_progressbar.visibility = View.GONE
//            }
//
//        })

    }


}