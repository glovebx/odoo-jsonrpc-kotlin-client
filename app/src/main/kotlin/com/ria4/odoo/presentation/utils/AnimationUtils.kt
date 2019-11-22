package com.ria4.odoo.presentation.utils

import android.view.animation.Interpolator
import android.view.animation.LinearInterpolator
import androidx.interpolator.view.animation.FastOutLinearInInterpolator
import androidx.interpolator.view.animation.FastOutSlowInInterpolator

/**
 * Created by glovebx on 06.08.2017.
 */

object AnimationUtils {

    val LINEAR_INTERPOLATOR: Interpolator = LinearInterpolator()
    val FAST_OUT_SLOW_IN_INTERPOLATOR: Interpolator = FastOutSlowInInterpolator()
    val FAST_OUT_LINEAR_IN_INTERPOLATOR: Interpolator = FastOutLinearInInterpolator()

    fun lerp(startValue: Int, endValue: Int, fraction: Float): Int {
        return startValue + Math.round(fraction * (endValue - startValue))
    }
}