package com.ria4.odoo.presentation.widget

import android.content.Context
import android.util.AttributeSet
import androidx.cardview.widget.CardView

/**
 * Created by glovebx on 08.08.2017.
 */

class SquareCardView constructor(context: Context, attrs: AttributeSet? = null) : CardView(context, attrs) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
    }
}