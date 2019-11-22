package com.wuhenzhizao.adapter.extension.dragSwipeDismiss

/**
 * Created by liufei on 2017/12/17.
 */
interface DragAndDismissInterface<in VH> {

    fun onChildDraw(target: VH, dX: Float, dY: Float)

    // 松开手指
    fun onSingleTapUp()

    fun onItemMoving(target: VH?, actionState: Int)

    fun onItemComplete(target: VH)

    fun onItemDrag(from: VH, target: VH)

    fun onItemSwipe(holder: VH, direction: Int)
}