package com.ria4.odoo.presentation.screen.dispatch

import android.content.Intent
import com.ria4.odoo.presentation.base_mvp.base.BaseActivity
import com.ria4.odoo.presentation.screen.auth.AuthActivity
import com.ria4.odoo.presentation.screen.home.HomeActivity
import com.ria4.odoo.presentation.utils.extensions.start
import javax.inject.Inject

class DispatchActivity : BaseActivity<DispatchContract.View, DispatchContract.Presenter>(), DispatchContract.View {

    @Inject
    protected lateinit var dispatchPresenter: DispatchPresenter

    override fun injectDependencies() {
        activityComponent.inject(this)
    }

    override fun initPresenter() = dispatchPresenter

    override fun taskGuard() {
        // 避免从桌面启动程序后，会重新实例化入口类的activity
        if (!this.isTaskRoot) { // 当前类不是该Task的根部，那么之前启动
            intent?.run {
                if (this.hasCategory(Intent.CATEGORY_LAUNCHER)
                        && Intent.ACTION_MAIN == this.action) { // 当前类是从桌面启动的
                    finish() // finish掉该类，直接打开该Task中现存的Activity
                    return
                }
            }
        }
    }

    override fun openHomeActivity() {
        start<HomeActivity>()
        finish()
    }

    override fun openLoginActivity() {
        start<AuthActivity>()
        finish()
    }

    override fun showError(message: String?) {
        showErrorDialog(message)
    }

}
