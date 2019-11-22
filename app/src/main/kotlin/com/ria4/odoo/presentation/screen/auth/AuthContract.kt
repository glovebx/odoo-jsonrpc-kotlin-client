package com.ria4.odoo.presentation.screen.auth

import android.content.Intent
import android.net.Uri
import com.ria4.odoo.presentation.base_mvp.api.ApiContract

/**
 * Created by glovebx on 11.11.2019.
 */
interface AuthContract {

    interface View : ApiContract.View {

        fun showVersion(version: String)

        fun startOAuthIntent(uri: Uri)

        fun openHomeActivity()
    }

    interface Presenter : ApiContract.Presenter<View> {

        fun init()

        fun makeLogin()

        fun checkLogin(resultIntent: Intent?)
    }
}