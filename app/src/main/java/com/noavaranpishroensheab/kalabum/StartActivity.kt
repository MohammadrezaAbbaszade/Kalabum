package com.noavaranpishroensheab.kalabum

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class StartActivity : AppCompatActivity() {

    companion object {
        fun newIntent(context: Context): Intent {
            val intent = Intent(context, StartActivity::class.java)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (SharePreferenceData.getToken(this) == null) {
            SharePreferenceData.setToken(
                this,
                "Bearer " + "2|TwdOKlDq2bGuUgP0vvYE1YxBeNxqNjm25uNbD8Kj"
            )
            val intent = LoginActivity.newIntent(this)
            startActivity(intent)
        } else {
            val intent = MainActivity.newIntent(this)
            startActivity(intent)
        }

        finish()


    }
}