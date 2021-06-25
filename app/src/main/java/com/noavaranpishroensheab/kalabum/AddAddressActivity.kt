package com.noavaranpishroensheab.kalabum

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.carto.styles.AnimationStyleBuilder
import com.carto.styles.AnimationType
import com.carto.styles.MarkerStyleBuilder
import kotlinx.android.synthetic.main.activity_apply_order.*
import kotlinx.android.synthetic.main.toolbar.*
import org.neshan.common.model.LatLng
import org.neshan.mapsdk.internal.utils.BitmapUtils
import org.neshan.mapsdk.model.Marker

class AddAddressActivity : AppCompatActivity() {
    val ACTIVITY_RESULT_CODE = 0

    companion object {
        fun newIntent(context: Context): Intent {
            val intent = Intent(context, AddAddressActivity::class.java)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apply_order)
        toolbar_back.visibility = View.VISIBLE
        toolbar_menu.visibility = View.GONE
        toolbar_back.setOnClickListener {
            finish()
        }
        toolbar_sub_back.setOnClickListener {
            finish()
        }
        submit_order.setOnClickListener {
            val intent = OrderStatusActivity.newIntent(this)
            startActivity(intent)
        }
        apply_add_address_btn.setOnClickListener {
            val intent = Intent(this, AddLabelActivity::class.java)
            startActivityForResult(intent, ACTIVITY_RESULT_CODE)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ACTIVITY_RESULT_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                val address = data?.getStringExtra(AddLabelActivity.getAddressForResult())
                apply_address.setText(address)
                val LatLng = LatLng(
                    data?.getDoubleExtra(AddLabelActivity.getLatitudeForResult(), 0.0)!!
                    , data?.getDoubleExtra(AddLabelActivity.getLongitudeForResult(), 0.0)!!
                )
                apply_order_sub_map.moveCamera(LatLng, 0F);
                apply_order_sub_map.setZoom(15F, 0.25f);
                apply_order_sub_map.addMarker(createMarker(LatLng))
            }
        }
    }

    private fun createMarker(loc: LatLng): Marker {
        // Creating animation for marker. We should use an object of type AnimationStyleBuilder, set
        // all animation features on it and then call buildStyle() method that returns an object of type
        // AnimationStyle
        val animStBl = AnimationStyleBuilder()
        animStBl.fadeAnimationType = AnimationType.ANIMATION_TYPE_SMOOTHSTEP
        animStBl.sizeAnimationType = AnimationType.ANIMATION_TYPE_SPRING
        animStBl.phaseInDuration = 0.5f
        animStBl.phaseOutDuration = 0.5f


        // Creating marker style. We should use an object of type MarkerStyleCreator, set all features on it
        // and then call buildStyle method on it. This method returns an object of type MarkerStyle
        val markStCr = MarkerStyleBuilder()
        markStCr.size = 30f

        markStCr.bitmap = BitmapUtils.createBitmapFromAndroidBitmap(
            BitmapFactory.decodeResource(
                resources,
                R.drawable.ic_location_red
            )
        )
        // AnimationStyle object - that was created before - is used here
        markStCr.animationStyle = animStBl.buildStyle()
        val markSt = markStCr.buildStyle()

        // Creating marker
        return Marker(loc, markSt)
    }
}