package com.ria4.odoo.presentation.screen.auth

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.ria4.odoo.R
import com.ria4.odoo.presentation.base_mvp.base.BaseActivity
import com.ria4.odoo.presentation.screen.home.HomeActivity
import com.ria4.odoo.presentation.utils.extensions.onClick
import com.ria4.odoo.presentation.utils.extensions.showToast
import com.ria4.odoo.presentation.utils.extensions.start
import com.ria4.odoo.presentation.utils.extensions.takeColor
import kotlinx.android.synthetic.main.activity_auth.*
import kotlinx.android.synthetic.main.progress_bar.*
import javax.inject.Inject

class AuthActivity : BaseActivity<AuthContract.View, AuthContract.Presenter>(), AuthContract.View {

    @Inject
    protected lateinit var authPresenter: AuthPresenter

    override fun initPresenter(): AuthContract.Presenter = authPresenter

    override fun injectDependencies() {
        activityComponent.inject(this)
    } 

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        progressBar.backgroundCircleColor = takeColor(com.ria4.odoo.R.color.colorPrimary)
        login.onClick {
            presenter.makeLogin()
        }

        presenter.init()
    }

    override fun showVersion(version: String) {
        textView2.text = textView2.text.toString() + version
    }

    override fun showLoading() {
        progressBar.start()
        login.isClickable = false
    }

    override fun hideLoading() {
        progressBar.stop()
        login.isClickable = true
    }

    override fun onDestroy() {
        progressBar.dismiss()
        super.onDestroy()
    }

    override fun showError(message: String?) {
        showErrorDialog(message)
    }

    override fun startOAuthIntent(uri: Uri) {
        startActivity(Intent(Intent.ACTION_VIEW, uri))
    }

    override fun openHomeActivity() {
        start<HomeActivity>()
        finish()
        showToast(getString(com.ria4.odoo.R.string.logged_in_message))
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        presenter.checkLogin(intent)
    }

}
