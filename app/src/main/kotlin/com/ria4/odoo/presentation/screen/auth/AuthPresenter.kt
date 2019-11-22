package com.ria4.odoo.presentation.screen.auth

import android.content.Intent
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import com.ria4.odoo.data.request.BaseRPCRequest
import com.ria4.odoo.data.request.CommonRPCRequest
import com.ria4.odoo.di.scope.PerActivity
import com.ria4.odoo.domain.fetcher.result_listener.RequestType
import com.ria4.odoo.domain.interactor.UserInteractor
import com.ria4.odoo.presentation.base_mvp.api.ApiPresenter
import com.ria4.odoo.presentation.utils.extensions.log
import javax.inject.Inject

/**
 * Created by glovebx on 11.11.2019.
 */
@PerActivity
class AuthPresenter @Inject constructor(private val userInteractor: UserInteractor)
    : ApiPresenter<AuthContract.View>(), AuthContract.Presenter {

    @OnLifecycleEvent(value = Lifecycle.Event.ON_START)
    fun onStart() {
        if (AUTH statusIs SUCCESS)
            view?.showLoading()
    }

    override fun init() {
        // 先获取服务端版本
        fetch(userInteractor.getServerVersion(), COMMON) {
            // 显示到界面
            view?.showVersion(it.serverVersion)
            // 继续获取db list
            fetch(userInteractor.listDb(), COMMON) {it2 ->
                log(it2)
            }
        }
    }

    override fun makeLogin() {
//        view?.startOAuthIntent(Uri.parse(ApiConstants.LOGIN_OAUTH_URL))

    }

    override fun checkLogin(resultIntent: Intent?) {
        val userCode: String? = resultIntent?.data?.getQueryParameter("code")
//        userCode?.let {
//            fetch(userInteractor.getUser(it), AUTH) {
//                view?.hideLoading()
//                view?.openHomeActivity()
//            }
//        }
    }

    override fun onRequestStart(requestType: RequestType) {
        super.onRequestStart(requestType)
        if (requestType == AUTH) {
            view?.showLoading()
        }
    }

    override fun onRequestError(errorMessage: String?) {
        view?.apply {
            hideLoading()
            showError(errorMessage)
        }
    }
}