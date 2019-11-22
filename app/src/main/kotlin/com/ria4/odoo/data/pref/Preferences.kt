package com.ria4.odoo.data.pref

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.ria4.odoo.data.network.ApiConstants
import com.ria4.odoo.presentation.utils.extensions.emptyString
import com.ria4.odoo.presentation.utils.extensions.unSafeLazy
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by glovebx on 11.11.2019.
 */
@Singleton
class Preferences @Inject constructor(app: Application) {

    private val SHARED_PREF_NAME = "odoo_shared_pref"
    private val USER_LOGGED_IN = "user_logged_in"
    private val USER_TOKEN = "user_token"

    private val sharedPreferences by unSafeLazy {
        app.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
    }
    val isUserLoggedIn
        get() = sharedPreferences.getBoolean(USER_LOGGED_IN, false)
    val token: String
        get() = sharedPreferences.getString(USER_TOKEN, ApiConstants.TOKEN)

    fun saveUserLoggedIn() {
        sharedPreferences.put {
            putBoolean(USER_LOGGED_IN, true)
        }
    }

    fun saveUserLoggedOut() {
        sharedPreferences.put {
            putBoolean(USER_LOGGED_IN, false)
        }
    }

    infix fun saveUserToken(token: String?) {
        sharedPreferences.put {
            putString(USER_TOKEN, token)
        }
    }

    fun deleteToken() {
        sharedPreferences.put {
            putString(USER_TOKEN, emptyString)
        }
    }
}

private inline fun SharedPreferences.put(body: SharedPreferences.Editor.() -> Unit) {
    val editor = this.edit()
    editor.body()
    editor.apply()
}