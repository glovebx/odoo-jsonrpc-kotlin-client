package com.ria4.odoo.domain.fetcher

import com.ria4.odoo.domain.fetcher.result_listener.RequestType
import com.ria4.odoo.domain.fetcher.result_listener.ResultListener
import com.ria4.odoo.presentation.utils.CompositeDisposableWrapper
import com.ria4.odoo.presentation.utils.extensions.isNull
import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by glovebx on 11.11.2019.
 */
@Singleton
class Fetcher @Inject constructor(private val disposable: CompositeDisposableWrapper) {

    private val requestMap = ConcurrentHashMap<RequestType, Status>()

    private fun <T> getIOToMainTransformer(): SingleTransformer<T, T> {
        return SingleTransformer {
            it.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }
    }

    fun <T> fetch(callerName: String, flowable: Flowable<T>, requestType: RequestType,
                  resultListener: ResultListener, success: (T) -> Unit) {
        val d = flowable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { resultListener startAndAdd requestType }
                .subscribe(onSuccess<T>(requestType, success),
                        resultListener.onError(requestType))
        disposable.add(callerName, d)
    }

    fun <T> fetch(callerName: String, observable: Observable<T>, requestType: RequestType,
                  resultListener: ResultListener, success: (T) -> Unit) {
        val d = observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { resultListener startAndAdd requestType }
                .subscribe(onSuccess<T>(requestType, success),
                        resultListener.onError(requestType))
        disposable.add(callerName, d)
    }

    fun <T> fetch(callerName: String, single: Single<T>, requestType: RequestType,
                  resultListener: ResultListener, success: (T) -> Unit) {
        val d = single
                .compose(getIOToMainTransformer())
                .doOnSubscribe { resultListener startAndAdd requestType }
                .subscribe(onSuccess<T>(requestType, success),
                        resultListener.onError(requestType))
        disposable.add(callerName, d)
    }

    fun complete(callerName: String, completable: Completable, requestType: RequestType,
                 resultListener: ResultListener, success: () -> Unit) {
        val d = completable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { resultListener startAndAdd requestType }
                .subscribe({
                    requestMap.replace(requestType, Status.SUCCESS)
                    success()
                }, resultListener.onError(requestType))
        disposable.add(callerName, d)
    }

    private infix fun ResultListener.startAndAdd(requestType: RequestType) {
        onRequestStart(requestType)
        if (requestType != RequestType.TYPE_NONE)
            requestMap.put(requestType, Status.LOADING)
    }

    private fun ResultListener.onError(requestType: RequestType): (Throwable) -> Unit {
        return {
            requestMap.replace(requestType, Status.ERROR)
            onRequestError(requestType, it.message)
        }
    }

    private fun <T> onSuccess(requestType: RequestType, success: (T) -> Unit): (T) -> Unit {
        return {
            val status = if (it is List<*> && it.isEmpty()) {
                Status.EMPTY_SUCCESS
            } else {
                Status.SUCCESS
            }
            requestMap.replace(requestType, status)
            success(it)
        }
    }

    fun hasActiveRequest(): Boolean = requestMap.isNotEmpty()

    fun getRequestStatus(requestType: RequestType) = requestMap.getOrElse(requestType, { Status.IDLE })

    fun removeRequest(requestType: RequestType) {
        requestMap.remove(requestType)
    }

    fun dispose(callerName: String) {
        disposable.dispose(callerName)
    }

    @Deprecated("No need anymore")
    fun clear() {
        disposable.clear()
    }
}