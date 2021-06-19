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
import kotlinx.android.synthetic.main.activity_category.*
import kotlinx.android.synthetic.main.category_items.view.*
import kotlinx.android.synthetic.main.sub_category_options_item.view.*
import kotlinx.android.synthetic.main.toolbar.*


class CategoryActivity : AppCompatActivity() {
    lateinit var categoryViewModel: CategoryViewModel
    lateinit var queris: HashMap<String, Int>
    lateinit var categoryAdapter: CategoryAdapter

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
                var categoryAdapter = CategoryAdapter(this, true, false, it.data.categories)
                categoryAdapter.setOnClickListener(object : CategoryAdapter.SubCategoryClicked {
                    override fun clicked(parentId: Int) {
                        val intent = newIntent(this@CategoryActivity, true, parentId)
                        startActivity(intent)
                    }

                    override fun subClicked(id: Int) {
                        categoryViewModel.getSubCategoriesOptions(
                            SharePreferenceData.getToken(this@CategoryActivity).toString(), id
                        )
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
                categoryAdapter = CategoryAdapter(this, false, false, it.data.categories)
                categoryAdapter.setOnClickListener(object : CategoryAdapter.SubCategoryClicked {
                    override fun clicked(parentId: Int) {
                    }

                    override fun subClicked(id: Int) {

                        categoryViewModel.getSubCategoriesOptions(
                            SharePreferenceData.getToken(this@CategoryActivity).toString(), id
                        )

                        categoryViewModel.mSubCategoriesOptions?.observe(
                            this@CategoryActivity,
                            Observer<SubCategoriesResponse> {


                                if (it.success) {

                                    categoryAdapter.isCategoryOption = true
                                    categoryAdapter.isCategory = false
                                    categoryAdapter.setSunCategoryOptionData(it.data)

                                } else {
                                    Toast.makeText(
                                        this@CategoryActivity,
                                        it.message,
                                        Toast.LENGTH_LONG
                                    ).show()
                                }

                            })


                    }

                })
                category_recyclerView.adapter = categoryAdapter

            } else {
                category_progressbar.visibility = View.GONE
            }

        })


    }


    class CategoryAdapter(
        val context: Context,
        var isCategory: Boolean,
        var isCategoryOption: Boolean,
        var categories: List<Categories>,
        var subCategoryClicked: SubCategoryClicked? = null
    ) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {
        lateinit var subCategoryOptionData: SubCategoriesData
        lateinit var holder: ViewHolder
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

                if (!isCategory) {
                    if (!isCategoryOption) {
                        var expanded = categories.get(position).isExapnded

                        categories.get(position).isExapnded = !expanded
                        this.holder=holder
                        subCategoryClicked?.subClicked(categories.get(position).id)
                        // Notify the adapter that item has changed
                        notifyItemChanged(position);

                    } else {
                        holder.itemView.sub_category_progress_bar.visibility = View.GONE
                        holder.itemView.sub_category_recyclerView.adapter =
                            SubCategoryOptionAdapter(context, subCategoryOptionData)

                    }
                } else {
                    subCategoryClicked?.clicked(categories.get(position).id)
                }


            }
        }

        interface SubCategoryClicked {
            fun clicked(parentId: Int)
            fun subClicked(id: Int)
        }

        fun setOnClickListener(listener: SubCategoryClicked) {
            this.subCategoryClicked = listener
        }

        fun setSunCategoryOptionData(subCategoryOptionData: SubCategoriesData) {
            this.subCategoryOptionData = subCategoryOptionData
            holder.itemView.sub_category_progress_bar.visibility = View.GONE
            holder.itemView.sub_category_recyclerView.adapter =
                SubCategoryOptionAdapter(context, subCategoryOptionData)
        }

        class ViewHolder(private val view: View, val context: Context) :
            RecyclerView.ViewHolder(view) {


            fun bind(categories: Categories) {
                with(view) {
                    val expanded: Boolean = categories.isExapnded
                    category_item_txt.text = categories.name
                    category_sub_root_view.setVisibility(if (expanded) View.VISIBLE else View.GONE)
                }


            }


        }


    }


    class SubCategoryOptionAdapter(
        val context: Context,
        var subCategoryOptionData: SubCategoriesData
    ) : RecyclerView.Adapter<SubCategoryOptionAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(context)
                .inflate(R.layout.sub_category_options_item, parent, false)
            return ViewHolder(view, context)
        }

        override fun getItemCount(): Int {
            return subCategoryOptionData.items.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(subCategoryOptionData.items.get(position))
        }


        class ViewHolder(private val view: View, val context: Context) :
            RecyclerView.ViewHolder(view) {


            fun bind(item: Item) {
                with(view) {
                    sub_category_option_name.text = item.name
                }


            }


        }


    }


}