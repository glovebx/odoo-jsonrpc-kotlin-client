package com.wuhenzhizao.adapter.extension.dragSwipeDismiss

import android.view.View
import com.wuhenzhizao.adapter.interfaces.Listener

/**
 * Item 移动中触发
 */
interface ItemMovingListener<in VH> : Listener<VH> {
    /**
     * @param target 移动的位置
     * @param actionState 状态标记
     */
    fun onItemMoving(target: Int, actionState: Int)
}

/**
 * Item 绘制图形中触发
 */
interface ItemDrawListener<in VH> : Listener<VH> {
    /**
     * @param target 移动的位置
     * @param actionState 状态标记
     */
    fun onChildDraw(view: View, target: Int, dX: Float, dY: Float)
}

/**
 * Item 松开手指时触发
 */
interface ItemGestureListener<in VH> : Listener<VH> {

    fun onSingleTapUp()
}

/**
 * Item 移动完成后触发
 */
interface ItemCompleteListener<in VH> : Listener<VH> {
    /**
     * @param target 移动的位置
     */
    fun onItemComplete(target: Int)
}

/**
 * Item 被拖拽时触发
 */
interface ItemDragListener<in VH> : Listener<VH> {
    /**
     * @param from 开始位置
     * @param target 结束位置
     */
    fun onItemDrag(from: Int, target: Int)
}

/**
 * Item 滑动时触发
 */
interface ItemSwipeListener<in VH> : Listener<VH> {
    /**
     * @param position
     * @param direction : 方向{ @link ItemTouchHelper.Left...}
     */
    fun onItemSwipe(position: Int, direction: Int)
}