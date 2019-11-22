package com.ria4.odoo.presentation.base_mvp.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import io.armcha.arch.BaseMVPActivity
import com.ria4.odoo.App
import com.ria4.odoo.di.component.ActivityComponent
import com.ria4.odoo.di.module.ActivityModule
import com.ria4.odoo.presentation.navigation.Navigator
import com.ria4.odoo.presentation.utils.S
import com.ria4.odoo.presentation.utils.extensions.emptyString
import com.ria4.odoo.presentation.utils.extensions.unSafeLazy
import com.ria4.odoo.presentation.widget.MaterialDialog
import kotlinx.android.extensions.CacheImplementation
import kotlinx.android.extensions.ContainerOptions
import javax.inject.Inject

abstract class BaseActivity<V : BaseContract.View, P : BaseContract.Presenter<V>>
    : BaseMVPActivity<V, P>(), Navigator.FragmentChangeListener {

    @Inject
    lateinit var navigator: Navigator

    @Inject
    lateinit var inflater: LayoutInflater

    private var dialog: MaterialDialog? = null

    val activityComponent: ActivityComponent by unSafeLazy {
        getAppComponent() + ActivityModule(this)
    }

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies()
        navigator.fragmentChangeListener = this
        super.onCreate(savedInstanceState)
    }

    @CallSuper
    override fun onDestroy() {
        dialog?.dismiss()
        super.onDestroy()
    }

    @CallSuper
    override fun onBackPressed() {
        if (navigator.hasBackStack())
            navigator.goBack()
        else
            super.onBackPressed()
    }

    protected abstract fun injectDependencies()

    private fun getAppComponent() = App.instance.applicationComponent

    inline protected fun <reified T : Fragment> goTo(keepState: Boolean = true,
                                                     withCustomAnimation: Boolean = false,
                                                     arg: Bundle = Bundle.EMPTY) {
        navigator.goTo<T>(keepState = keepState,
                withCustomAnimation = withCustomAnimation,
                arg = arg)
    }

    fun showDialog(title: String, message: String, buttonText: String = "Close") {
        dialog = MaterialDialog(this).apply {
            message(message)
                    .title(title)
                    .addPositiveButton(buttonText) {
                        hide()
                    }
                    .show()
        }
    }

    fun showErrorDialog(message: String?, buttonText: String = "Close") {
        showDialog(getString(S.error_title), message ?: emptyString, buttonText)
    }
}
