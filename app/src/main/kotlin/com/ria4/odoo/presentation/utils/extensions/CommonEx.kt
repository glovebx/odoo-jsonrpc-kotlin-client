package com.ria4.odoo.presentation.utils.extensions

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Service
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Build
import android.os.Handler
import android.text.Html
import android.text.Spanned
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.ria4.odoo.App
import com.ria4.odoo.Config
import com.ria4.odoo.presentation.utils.Experimental


/**
 * Created by glovebx on 01.08.2017.
 */

infix fun Context.takeColor(colorId: Int) = ContextCompat.getColor(this, colorId)

operator fun Context.get(resId: Int): String = getString(resId)

operator fun Fragment.get(resId: Int): String = getString(resId)

@Experimental
fun Int.text(): String = App.instance.getString(this) //What do you think about it?

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

inline fun delay(milliseconds: Long, crossinline action: () -> Unit) {
    Handler().postDelayed({
        action()
    }, milliseconds)
}

//@Deprecated("Use emptyString instead", ReplaceWith("emptyString"), level = DeprecationLevel.WARNING)
//fun emptyString() = ""

const val emptyString = ""

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
inline fun LorAbove(body: () -> Unit) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        body()
    }
}

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
inline fun MorAbove(body: () -> Unit) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        body()
    }
}

@TargetApi(Build.VERSION_CODES.N)
inline fun NorAbove(body: () -> Unit) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        body()
    }
}

@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
inline fun MR2orAbove(body: () -> Unit) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
        body()
    }
}


@SuppressLint("NewApi")
fun String.toHtml(): Spanned {
    NorAbove {
        return Html.fromHtml(this, Html.FROM_HTML_MODE_COMPACT)
    }
    return Html.fromHtml(this)
}

fun Int.toPx(context: Context): Int {
    val density = context.resources.displayMetrics.density
    return (this * density).toInt()
}

fun <T> unSafeLazy(initializer: () -> T): Lazy<T> {
    return lazy(LazyThreadSafetyMode.NONE) {
        initializer()
    }
}

fun Int.isZero(): Boolean = this == 0

fun String.copyToClipboard(context: Context) {
    (context.getSystemService(Service.CLIPBOARD_SERVICE) as ClipboardManager).primaryClip = ClipData.newPlainText(Config.APP_CLIP_KEY_SELF, this)
}

inline fun <F, S> doubleWith(first: F, second: S, runWith: F.(S) -> Unit) {
    first.runWith(second)
}

val Any?.isNull: Boolean
    get() = this == null