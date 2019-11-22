package com.allco.ui.bottomsheet.utils

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.util.TypedValue
import androidx.annotation.AttrRes
import androidx.annotation.DrawableRes
import androidx.annotation.StyleRes
import androidx.appcompat.content.res.AppCompatResources
import com.allco.ui.bottomsheet.R

/**
 *  Retrieve `resource id` for the style declared in `parentStyleRes` and referenced by `styleAttrRef`
 *  @param parentStyleRes - the parent style resource id
 *  @param styleAttrRef - an attribute reference to the style
 *  @param defStyleRes - a fallback style resource id
 *  @return the Style's resource identifier, or defStyleRes if not defined.
 */
@StyleRes
fun Context.getStyleRes(@StyleRes parentStyleRes: Int, @AttrRes styleAttrRef: Int, @StyleRes defStyleRes: Int): Int {
    val attr = obtainStyledAttributes(parentStyleRes, intArrayOf(styleAttrRef))
    val styleRes = attr.getResourceId(0, defStyleRes)
    attr.recycle()
    return styleRes
}

@StyleRes
fun Context.getStyleRes(@AttrRes styleAttrRef: Int, @StyleRes defStyleRes: Int): Int {
    val attrs = obtainStyledAttributes(intArrayOf(styleAttrRef))
    val styleRes = attrs.getResourceId(0, defStyleRes)
    attrs.recycle()
    return styleRes
}

fun Context.getDrawableCompat(@DrawableRes drawableRes: Int?): Drawable? =
    drawableRes?.let { AppCompatResources.getDrawable(this, drawableRes) }

fun Context.getDrawableByAttr(@AttrRes attrDrawableRes: Int): Drawable? {
    val attr = TypedValue().also { theme.resolveAttribute(attrDrawableRes, it, true) }
    return if (attr.type >= TypedValue.TYPE_FIRST_COLOR_INT && attr.type <= TypedValue.TYPE_LAST_COLOR_INT) {
        // the resource is a color
        ColorDrawable(attr.data)
    } else {
        // the resource is not a color, probably a drawable
        getDrawableCompat(attr.resourceId)
    }
}

fun resolveTextAppearanceAttr(context: Context, @AttrRes styleAttrRef: Int, @StyleRes defaultStyleRes: Int): Int =
    context.getStyleRes(
        context.getStyleRes(R.attr.bottomSheetLibStyle, R.style.BottomSheetLib),
        styleAttrRef,
        defaultStyleRes
    )
