package com.ria4.odoo.presentation.widget

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView

/**
 * Created by glovebx on 16.02.2017.
 */

class AnimatedImageView(context: Context, attrs: AttributeSet? = null)
    : AppCompatImageView(context, attrs), AnimatedView {

    fun setAnimatedImage(newImage: Int, startDelay: Long = 0L) {
        changeImage(newImage, startDelay)
    }

    private fun changeImage(newImage: Int, startDelay: Long) {
        if (tag == newImage)
            return
        animate(view = this, startDelay = startDelay) {
            setImageResource(newImage)
            tag = newImage
        }
    }
}