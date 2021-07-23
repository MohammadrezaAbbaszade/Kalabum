package com.noavaranpishroensheab.kalabum

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.dialog_fragment_detail_option.view.*
import kotlinx.android.synthetic.main.options_value_item.view.*
import kotlinx.android.synthetic.main.sub_category_options_item.view.*

class DetailOptionDialogFragment : DialogFragment() {
    lateinit var values: List<String>


    companion object {
        val TITLE = "title"
        val VALUE_EXTRA = "valueExtra"
        fun newInstance(title: String): DetailOptionDialogFragment {
            val args = Bundle()
            args.putString(TITLE, title)
            val fragment = DetailOptionDialogFragment()
            fragment.arguments = args
            return fragment
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_fragment_detail_option, container, false)
        view.dialog_fragment_detail_title_text_view.text=arguments?.getString(TITLE,"")
        if (values != null) {
            view.dialog_fragment_detail_option_recycler.adapter =
                DetailOptionAdapter(context!!, values, object : DetailOptionAdapter.OptionClicked {
                    override fun clicked(option: String) {
                        val fragment = targetFragment
                        val intent = Intent()
                        intent.putExtra(VALUE_EXTRA, option)
                        fragment?.onActivityResult(
                            getTargetRequestCode(),
                            Activity.RESULT_OK,
                            intent
                        );
                        dialog?.cancel()
                    }

                })
        }
        return view
    }


    class DetailOptionAdapter(
        val context: Context,
        val values: List<String>,
        val optionClicked: OptionClicked
    ) : RecyclerView.Adapter<DetailOptionAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(context)
                .inflate(R.layout.options_value_item, parent, false)
            return ViewHolder(view, context)
        }

        override fun getItemCount(): Int {
            return values.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(values.get(position))
            holder.itemView.setOnClickListener {
                optionClicked.clicked(values.get(position))
            }
        }

        interface OptionClicked {
            fun clicked(option: String)
        }

        class ViewHolder(private val view: View, val context: Context) :
            RecyclerView.ViewHolder(view) {


            fun bind(item: String) {
                with(view) {
                    option_value_item_name.text = item
                }


            }


        }


    }

}