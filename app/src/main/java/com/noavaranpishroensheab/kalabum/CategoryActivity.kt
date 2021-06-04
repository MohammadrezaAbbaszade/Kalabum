package com.noavaranpishroensheab.kalabum

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.toolbar.*

class CategoryActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)
        toolbar_back.visibility = View.VISIBLE
        toolbar_search.visibility = View.VISIBLE


    }
}