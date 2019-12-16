package com.ria4.odoo.presentation.base_mvp.api

import androidx.annotation.CallSuper
import com.ria4.odoo.domain.fetcher.Fetcher
import com.ria4.odoo.domain.fetcher.Status
import com.ria4.odoo.domain.fetcher.result_listener.RequestType
import com.ria4.odoo.domain.fetcher.result_listener.ResultListener
import com.ria4.odoo.presentation.base_mvp.base.BaseContract
import com.ria4.odoo.presentation.base_mvp.base.BasePresenter
import com.ria4.odoo.presentation.utils.extensions.emptyString
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by glovebx on 11.11.2019.
 */

abstract class ApiPresenter<VIEW : BaseContract.View> : BasePresenter<VIEW>(), ResultListener {

    @Inject
    protected lateinit var fetcher: Fetcher

    private var callerName: String = emptyString

    private val TYPE_NONE = RequestType.TYPE_NONE
    protected val AUTH: RequestType = RequestType.AUTH
    protected val COMMON = RequestType.COMMON
    protected val DB = RequestType.DB
    protected val MODEL = RequestType.MODEL

    protected val SUCCESS = Status.SUCCESS
    protected val LOADING = Status.LOADING
    protected val ERROR = Status.ERROR
    protected val EMPTY_SUCCESS = Status.EMPTY_SUCCESS

    protected infix fun RequestType.statusIs(status: Status)
            = fetcher.getRequestStatus(this) == status

    protected val RequestType.status
        get() = fetcher.getRequestStatus(this)

    @CallSuper
    override fun onPresenterCreate() {
        super.onPresenterCreate()

        callerName = this.javaClass.canonicalName
    }

    fun <TYPE> fetch(flowable: Flowable<TYPE>,
                     requestType: RequestType = TYPE_NONE, success: (TYPE) -> Unit) {
        fetcher.fetch(callerName, flowable, requestType, this, success)
    }

    fun <TYPE> fetch(observable: Observable<TYPE>,
                     requestType: RequestType = TYPE_NONE, success: (TYPE) -> Unit) {
        fetcher.fetch(callerName, observable, requestType, this, success)
    }

    fun <TYPE> fetch(single: Single<TYPE>,
                     requestType: RequestType = TYPE_NONE, success: (TYPE) -> Unit) {
        fetcher.fetch(callerName, single, requestType, this, success)
    }

    fun complete(completable: Completable,
                 requestType: RequestType = TYPE_NONE, success: () -> Unit = {}) {
        fetcher.complete(callerName, completable, requestType, this, success)
    }

    @CallSuper
    override fun onPresenterDestroy() {
        super.onPresenterDestroy()
        // 关联的Activity销毁时会进入该方法
        // TODO: 新界面进入时如果有请求可能会被clear！！！
        // 放到各个Presenter中分别处理
//        fetcher.clear()
        fetcher.dispose(callerName)
    }

    @CallSuper
    override fun onRequestStart(requestType: RequestType) {
        onRequestStart()
    }

    @CallSuper
    override fun onRequestError(requestType: RequestType, errorMessage: String?) {
        onRequestError(errorMessage)
    }
}