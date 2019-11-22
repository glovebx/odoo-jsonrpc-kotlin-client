package com.ria4.odoo.presentation.widget

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

/**
 * Created by glovebx on 16.02.2017.
 */

class AnimatedTextView (context: Context, attrs: AttributeSet? = null)
    : AppCompatTextView(context, attrs), AnimatedView {

    fun setAnimatedText(text: CharSequence, startDelay: Long = 0L) {
        changeText(text, startDelay)
    }

    private fun changeText(newText: CharSequence, startDelay: Long) {
        if (text == newText)
            return
        animate(view = this, startDelay = startDelay) {
            text = newText
        }
    }
}
