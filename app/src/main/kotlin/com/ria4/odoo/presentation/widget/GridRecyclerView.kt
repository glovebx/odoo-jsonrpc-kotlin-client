package com.ria4.odoo.presentation.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.animation.GridLayoutAnimationController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView


/**
 * Created by glovebx on 17.09.2017.
 */
class GridRecyclerView (context: Context, attrs: AttributeSet) : RecyclerView(context, attrs) {
//
//    override fun attachLayoutAnimationParameters(child: View, params: ViewGroup.LayoutParams,
//                                                 index: Int, count: Int) {
//        val layoutManager = layoutManager
//        if (adapter != null && layoutManager is GridLayoutManager) {
////            val animationParams = GridLayoutAnimationController.AnimationParameters()
////            params.layoutAnimationParameters = animationParams
////
////            animationParams.count = count
////            animationParams.index = index
////
////            val columns = layoutManager.spanCount
////            animationParams.columnsCount = columns
////            animationParams.rowsCount = count / columns
////
////            val invertedIndex = count - 1 - index
////            animationParams.column = columns - 1 - invertedIndex % columns
////            animationParams.row = animationParams.rowsCount - 1 - invertedIndex / columns
//        } else {
//            super.attachLayoutAnimationParameters(child, params, index, count)
//        }
//    }
}