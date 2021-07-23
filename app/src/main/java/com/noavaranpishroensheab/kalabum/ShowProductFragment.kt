package com.noavaranpishroensheab.kalabum

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.noavaranpishroensheab.kalabum.viewmodels.CategoryViewModel
import com.smarteist.autoimageslider.IndicatorAnimations
import com.smarteist.autoimageslider.SliderViewAdapter
import kotlinx.android.synthetic.main.fragment_show_product.view.*
import kotlinx.android.synthetic.main.sub_category_options_item.view.*

class ShowProductFragment : Fragment() {
    lateinit var sliderAdapter: SliderAdapter
    lateinit var categoryViewModel: CategoryViewModel

    companion object {
        val CATEGORY_ID = "categoryId"
        fun newInstance(categoryId: Int): ShowProductFragment {
            val args = Bundle()
            args.putInt(CATEGORY_ID, categoryId)
            val fragment = ShowProductFragment()
            fragment.arguments = args
            return fragment
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        categoryViewModel = ViewModelProviders.of(this).get(CategoryViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_show_product, container, false)
        view.show_product_fragment_progress_bar.visibility = View.VISIBLE
        view.fragment_show_product_root_view.visibility = View.GONE
        sliderAdapter = SliderAdapter(context!!)
        view.show_product_fragment_imageSlider.setIndicatorAnimation(IndicatorAnimations.WORM)
        view.show_product_fragment_imageSlider.sliderAdapter = sliderAdapter


        view.show_product_fragment_submit_request_btn.setOnClickListener {
            val intent = AddAddressActivity.newIntent(activity!!)
            startActivity(intent)
        }


        categoryViewModel.getSubCategoriesOptions(
            SharePreferenceData.getToken(context!!).toString(), 21
        )




        categoryViewModel.mSubCategoriesOptions?.observe(
            viewLifecycleOwner,
            Observer<SubCategoriesResponse> {


                if (it.success) {
                    view.show_product_fragment_progress_bar.visibility = View.GONE
                    view.fragment_show_product_root_view.visibility = View.VISIBLE
                    view.show_product_fragment_recycler.adapter =
                        SubCategoryOptionAdapter(context!!, it.data, this)

                } else {
                    Toast.makeText(
                        context,
                        it.message,
                        Toast.LENGTH_LONG
                    ).show()
                }

            })



        return view
    }


    class SubCategoryOptionAdapter(
        val context: Context,
        var subCategoryOptionData: SubCategoriesData,
        val fragment: ShowProductFragment
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
            holder.itemView.setOnClickListener {

                val detailOptionDialogFragment =
                    DetailOptionDialogFragment.newInstance(subCategoryOptionData.items.get(position).name)

                detailOptionDialogFragment.values = subCategoryOptionData.items.get(position).values

                detailOptionDialogFragment.setTargetFragment(fragment, 0)

                detailOptionDialogFragment.show(
                    fragment.fragmentManager!!,
                    "detailOptionFragmentTag"
                )

            }
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


    class SliderAdapter(
        val context: Context
    ) : SliderViewAdapter<SliderAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup?): ViewHolder {
            val view = LayoutInflater.from(context)
                .inflate(R.layout.slider_image_view_item, parent, false)
            return ViewHolder(view, context)
        }


        override fun onBindViewHolder(viewHolder: ViewHolder?, position: Int) {
            viewHolder?.bind()


        }


        override fun getCount(): Int {
            return 3
        }


        class ViewHolder(private val view: View, val context: Context) :
            SliderViewAdapter.ViewHolder(view) {

            fun bind() {
                with(view) {

                }


            }

        }


    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) {
            return
        }


        if (data != null && requestCode == 0) {
            Toast.makeText(
                context,
                data.getStringExtra(DetailOptionDialogFragment.VALUE_EXTRA),
                Toast.LENGTH_SHORT
            ).show()

        }


    }


}