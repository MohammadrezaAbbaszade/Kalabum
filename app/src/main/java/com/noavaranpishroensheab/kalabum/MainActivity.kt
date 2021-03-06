package com.noavaranpishroensheab.kalabum

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*

class MainActivity : AppCompatActivity() {

    companion object {
        fun newIntent(context: Context): Intent {
            val intent = Intent(context, MainActivity::class.java)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        registered_invoices.setOnClickListener {
            val intent = FactorListActivity.newIntent(this)
            startActivity(intent)
        }
        track_preinvoices.setOnClickListener {
            val intent = InvoiceListActivity.newIntent(this)
            startActivity(intent)
        }
        view_requests.setOnClickListener {
            val intent = OrderStatusActivity.newIntent(this)
            startActivity(intent)
        }
        categories.setOnClickListener {
            val intent = CategoryActivity.newIntent(this, false, 0)
            startActivity(intent)
        }
        toolbar_menu.setOnClickListener {

            if (acvity_drawer.isDrawerOpen(Gravity.RIGHT)) {
                acvity_drawer.closeDrawer(Gravity.RIGHT)
            } else {
                acvity_drawer.openDrawer(Gravity.RIGHT)
            }
        }
        toolbar_sub_menu.setOnClickListener {

            if (acvity_drawer.isDrawerOpen(Gravity.START)) {
                acvity_drawer.closeDrawer(Gravity.START)
            } else {
                acvity_drawer.openDrawer(Gravity.START)
            }
        }
        setMenuItemsListeners()
    }


    private fun setMenuItemsListeners() {
        navigation.setNavigationItemSelectedListener(object :
            NavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    R.id.my_pre_invoices -> {
                        val intent = InvoiceListActivity.newIntent(this@MainActivity)
                        startActivity(intent)
                    }

                    R.id.my_invoices -> {
                        val intent = FactorListActivity.newIntent(this@MainActivity)
                        startActivity(intent)
                    }

                    R.id.my_categories ->{
                        val intent = CategoryActivity.newIntent(this@MainActivity, false, 0)
                        startActivity(intent)
                    }

                    R.id.my_requests->{
                        val intent = OrderStatusActivity.newIntent(this@MainActivity)
                        startActivity(intent)
                    }
                }

                return true
            }

        })
    }
}