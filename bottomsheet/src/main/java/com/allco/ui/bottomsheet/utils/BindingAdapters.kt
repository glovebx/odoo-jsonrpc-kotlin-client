package com.allco.ui.bottomsheet.utils

import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.AttrRes
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StyleRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.widget.TextViewCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat

@BindingAdapter(value = ["bslListItems"])
fun setRecyclerViewItems(recyclerView: RecyclerView, listItems: ObserverBasedAdapter.ItemList) {
    recyclerView.adapter = ObserverBasedAdapter(listItems)
}

@BindingAdapter(value = ["bslTextAppearanceStyle", "bslTextAppearanceAttr"], requireAll = false)
fun bslTextAppearance(textView: TextView, @StyleRes styleRes: Int?, @AttrRes attrRef: Int?) {
    (styleRes ?: attrRef?.let { attr -> resolveTextAppearanceAttr(textView.context, attr, -1).takeIf { it != -1 } })
        ?.also { style ->
            TextViewCompat.setTextAppearance(textView, style)
        }
}

@BindingAdapter(value = ["bslBackgroundDrawable", "bslBackgroundAttr"], requireAll = false)
fun bslBackground(view: View, drawable: Drawable?, @AttrRes drawableAttrRef: Int) {
    (drawable ?: view.context.getDrawableByAttr(drawableAttrRef))?.also { view.background = it }
}

@BindingAdapter(value = ["bslMarginLeft", "bslMarginRight"], requireAll = false)
fun setParentLayoutLeftMargins(view: View, leftMargin: Int?, rightMargin: Int?) {
    (view.layoutParams as? ViewGroup.MarginLayoutParams)?.also { lp ->
        leftMargin?.also { lp.leftMargin = it }
        rightMargin?.also { lp.rightMargin = it }
        view.parent?.requestLayout()
    }
}

@BindingAdapter(value = ["bslCompatLeftDrawableRes", "bslCompatLeftDrawableResTintColor"], requireAll = false)
fun bslCompatLeftDrawableRes(view: TextView, @DrawableRes drawableLeftRes: Int?, @ColorRes colorTintLeft: Int?) {
    modifyDrawable(view, drawableLeftRes, colorTintLeft)?.also {
        view.setCompoundDrawablesWithIntrinsicBounds(it, null, null, null)
    }
}

@BindingAdapter(value = ["bslCompatLeftDrawable"], requireAll = false)
fun bslCompatLeftDrawable(view: TextView, drawableLeftRes: Drawable?) {
    drawableLeftRes?.also {
        view.setCompoundDrawablesWithIntrinsicBounds(it, null, null, null)
    }
}

private fun modifyDrawable(view: TextView, @DrawableRes drawableRes: Int?, @ColorRes colorRes: Int?): Drawable? {
    return drawableRes?.let { res ->
        val context = view.context
        return VectorDrawableCompat.create(context.resources, res, context.theme)?.also { drawable ->
            colorRes?.also { colorRes ->
                DrawableCompat.setTint(drawable, ContextCompat.getColor(context, colorRes))
            }
        }
    }
}