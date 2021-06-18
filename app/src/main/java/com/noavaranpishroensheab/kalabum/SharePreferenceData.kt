package com.noavaranpishroensheab.kalabum

import android.content.Context
import android.content.SharedPreferences

class SharePreferenceData {

    companion object {
        private val PREF_NAME = "kalabum"
        private val TOKEN = "token"
        private val LOGIN_RESULT = "loginResult"
        private fun getSharePreference(context: Context): SharedPreferences {

            return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

        }


        public fun setToken(context: Context, token: String) {

            val pref = getSharePreference(context)
            val editor = pref.edit()
            editor.putString(TOKEN, token)
            editor.apply()
        }

        public fun getToken(context: Context): String? {

            val pref: SharedPreferences? = getSharePreference(context)

            return pref?.getString(TOKEN, null)

        }

        public fun setLoginResult(context: Context, isLogin: Boolean) {

            val pref = getSharePreference(context)
            val editor = pref.edit()
            editor.putBoolean(LOGIN_RESULT, isLogin)
            editor.apply()
        }

        public fun getLoginResult(context: Context): Boolean? {

            val pref: SharedPreferences? = getSharePreference(context)

            return pref?.getBoolean(LOGIN_RESULT, false)

        }
    }


}