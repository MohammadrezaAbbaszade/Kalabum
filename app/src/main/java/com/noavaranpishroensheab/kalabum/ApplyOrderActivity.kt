package com.noavaranpishroensheab.kalabum

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_apply_order.*
import kotlinx.android.synthetic.main.toolbar.*

class ApplyOrderActivity : AppCompatActivity() {

    companion object {
        fun newIntent(context: Context): Intent {
            val intent = Intent(context, ApplyOrderActivity::class.java)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apply_order)
        toolbar_back.visibility = View.VISIBLE
        toolbar_menu.visibility=View.GONE
        toolbar_back.setOnClickListener {
            finish()
        }
        toolbar_sub_back.setOnClickListener {
            finish()
        }
        apply_add_address_btn.setOnClickListener {
            val intent = Intent(this, AddLabelActivity::class.java)
            startActivity(intent)
        }

    }


}