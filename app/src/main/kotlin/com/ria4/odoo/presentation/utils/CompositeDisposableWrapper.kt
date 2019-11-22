package com.ria4.odoo.presentation.utils

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.util.concurrent.ConcurrentHashMap

class CompositeDisposableWrapper () {
    private val callerDisposableMap = ConcurrentHashMap<String, CompositeDisposable>()

    fun add(callerName: String, d: Disposable) {
        val disposable = callerDisposableMap.getOrPut(callerName) {
            CompositeDisposable()
        }
        disposable.add(d)
    }

    fun dispose(callerName: String) {
        callerDisposableMap.remove(callerName)?.run {
            // set clear
            this.clear()
        }
    }

    @Deprecated("No need anymore")
    fun clear() {
        callerDisposableMap.values.forEach {
            it.clear()
        }
        callerDisposableMap.clear()
    }
}