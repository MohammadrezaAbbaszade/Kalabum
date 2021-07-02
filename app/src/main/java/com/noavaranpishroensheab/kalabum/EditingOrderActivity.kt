package com.noavaranpishroensheab.kalabum

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class EditingOrderActivity : AppCompatActivity() {



    companion object {
        fun newIntent(context: Context): Intent {
            val intent = Intent(context, EditingOrderActivity::class.java)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editing_order)






    }





}