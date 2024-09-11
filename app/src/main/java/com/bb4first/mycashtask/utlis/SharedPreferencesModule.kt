package com.bb4first.mycashtask.utlis

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.bb4first.mycashtask.MyCashTaskApplication
import com.bb4first.mycashtask.model.auth.User
import com.bb4first.mycashtask.utlis.Utils.logCat

object SharedPreferencesModule {
    private const val SHARED_PREFERENCES_NAME = "com.bb4first.mycashtask_SHARED_PREFERENCES_NAME"
    private val sharedPreferences: SharedPreferences =
        MyCashTaskApplication.instance.getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_PRIVATE)
    const val PREF_APP_LANGUAGE = "PREF_APP_LANGUAGE"
    const val PREF_APP_TOKEN = "PREF_APP_TOKEN"
    const val PREF_USER_DATA = "PREF_USER_DATA"
    const val PREF_FIREBASE_TOKEN = "PREF_FIREBASE_TOKEN"
    const val PREF_CART_ITEMS_COUNT = "PREF_CART_ITEMS_COUNT"

    fun setStringValue(key: String, value: String) {
        sharedPreferences.edit()?.apply {
            putString(key, value)
            apply()
        }
    }

    fun getStringValue(key: String, defaultValue: String): String {
        return sharedPreferences.getString(key, defaultValue) ?: defaultValue
    }

    fun setIntegerValue(key: String, value: Int) {
        sharedPreferences.edit()?.apply {
            putInt(key, value)
            apply()
        }
    }

    fun getIntegerValue(key: String, defaultValue: Int): Int {
        return sharedPreferences.getInt(key, defaultValue)
    }


    fun setUserValue(value: User) {
        value.toJson().logCat()
        sharedPreferences.edit()?.apply {
            putString(PREF_USER_DATA, value.toJson())
            apply()
        }
    }

    fun getUserValue(): User? {
        val userJson = sharedPreferences.getString(PREF_USER_DATA, null)
        return userJson?.let { User.fromJson(userJson) }
    }

    fun isLoggedIn(): Boolean {
        return sharedPreferences.contains(PREF_APP_TOKEN) && sharedPreferences.contains(
            PREF_USER_DATA
        )
    }

    fun logout() {
        sharedPreferences.edit()?.apply {
            remove(PREF_APP_TOKEN)
            remove(PREF_USER_DATA)
            apply()
        }
    }
}
