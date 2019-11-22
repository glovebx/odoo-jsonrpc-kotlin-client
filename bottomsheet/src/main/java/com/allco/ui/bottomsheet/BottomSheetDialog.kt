package com.allco.ui.bottomsheet

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.appcompat.view.ContextThemeWrapper
import androidx.lifecycle.LifecycleOwner
import com.allco.ui.bottomsheet.databinding.BottomSheetListBinding
import com.allco.ui.bottomsheet.utils.ObserverBasedAdapter
import com.allco.ui.bottomsheet.utils.getDrawableCompat
import com.allco.ui.bottomsheet.utils.getStyleRes
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlin.math.min
import kotlin.math.roundToInt

class BottomSheetDialog(context: Context, private val lifecycleOwner: LifecycleOwner) : BottomSheetDialog(
    ContextThemeWrapper(context, context.getStyleRes(R.attr.bottomSheetLibStyle, R.style.BottomSheetLib))
) {

    internal fun init(settings: BottomSheetSettings) {
        val binding = BottomSheetListBinding.inflate(layoutInflater)
        binding.items = convertToViewModelList(settings)
        binding.background =
            settings.backgroundDrawable ?: settings.backgroundRes?.let { context.getDrawableCompat(it) }

        setContentView(binding.root)

        @Suppress("UNUSED_ANONYMOUS_PARAMETER")
        binding.root.addOnLayoutChangeListener { view, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom ->
            val bottomSheet = findViewById<FrameLayout>(R.id.design_bottom_sheet)
                ?: throw IllegalStateException("R.id.design_bottom_sheet is not found")
            bottomSheet.setBackgroundColor(Color.TRANSPARENT)
            val maxInitialHeight = (getScreenHeight() * settings.maxInitialHeightInPercents / 100f).roundToInt()
            BottomSheetBehavior.from(bottomSheet).apply { peekHeight = min(bottomSheet.height, maxInitialHeight) }
        }

        setOnCancelListener { settings.onCanceled?.invoke() }
    }

    private fun convertToViewModelList(settings: BottomSheetSettings): ObserverBasedAdapter.ItemList {
        return ObserverBasedAdapter.ItemList().also { list ->
            for (item in settings.listItems) {
                // in case of linear BS use stack of linear VM's
                list += when (item) {
                    is BottomSheetSettings.TitleItem -> {
                        item.titleRes?.also { item.title = context.getString(it) }
                        TitleViewModelImpl(item)
                    }
                    is BottomSheetSettings.ClickableItem -> {
                        item.titleRes?.also { item.title = context.getString(it) }
                        ClickableViewModelImpl(item, this)
                    }
                    is BottomSheetSettings.DividerItem -> DividerViewModelImpl(item)
                    is BottomSheetSettings.CustomItem -> CustomItemViewModel(item, lifecycleOwner, this)
                    else -> throw IllegalArgumentException("`item` has unknown type")
                }
            }
        }
    }

    private fun getScreenHeight(): Int {
        return Resources.getSystem().displayMetrics.heightPixels
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        // make statusBar translucent
        window?.takeIf { Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT }?.apply {
            addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }
        super.onCreate(savedInstanceState)
    }
}
