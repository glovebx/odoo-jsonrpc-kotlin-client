package com.ria4.odoo.presentation.utils.extensions

import com.luseen.logger.Logger


/**
 * Created by glovebx on 14.09.2017.
 */

inline fun log(message: () -> Any?) {
    Logger.log(message())
}

inline fun <reified T> T.withLog(): T {
    log("${T::class.java.simpleName} $this")
    return this
}

fun log(vararg message: () -> Any?) {
    message.forEach {
        log(it())
    }
}

fun log(message: Any?) {
    Logger.log(message)
}