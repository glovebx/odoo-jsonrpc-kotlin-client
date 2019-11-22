package com.wuhenzhizao.adapter.extension.dragSwipeDismiss

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import androidx.annotation.CallSuper
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.wuhenzhizao.adapter.holder.RecyclerViewHolder

/**
 * Created by liufei on 2017/12/4.
 */
class DragAndSwipeRecyclerView(context: Context, attrs: AttributeSet?, defStyle: Int) : RecyclerView(context, attrs, defStyle) {
    var isLongPressDragEnable: Boolean = false
    var isItemViewSwipeEnable: Boolean = false
    var dragDirection: Int = ItemTouchHelper.UP or ItemTouchHelper.DOWN
    var swipeDirection: Int = ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT

    lateinit var touchHelper: ItemTouchHelper

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    override fun getAdapter(): DragAndSwipeRecyclerViewAdapter<*> {
        return super.getAdapter() as DragAndSwipeRecyclerViewAdapter<*>
    }

    override fun setAdapter(adapter: Adapter<*>?) {
//        val callback = ItemTouchHelperCallBack()
//        val touchHelper = ItemTouchHelper(callback)
        touchHelper = ItemTouchHelper(ItemTouchHelperCallBack())
        touchHelper.attachToRecyclerView(this)

        super.setAdapter(adapter)
    }

    inner class ItemTouchHelperCallBack : ItemTouchHelper.Callback() {

        override fun isLongPressDragEnabled(): Boolean = isLongPressDragEnable

        override fun isItemViewSwipeEnabled(): Boolean = isItemViewSwipeEnable

        override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
            return ItemTouchHelper.Callback.makeMovementFlags(dragDirection, swipeDirection)
        }

        @CallSuper
        override fun onSelectedChanged(viewHolder: ViewHolder?, actionState: Int) {
            super.onSelectedChanged(viewHolder, actionState)
            if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
                // 表示正在拖拽中
                adapter.onItemMoving(viewHolder as RecyclerViewHolder, actionState)
            }
        }

        @CallSuper
        override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: ViewHolder,
                        dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            // 判断是否跟删除区域相交
            adapter.onChildDraw(viewHolder as RecyclerViewHolder, dX, dY)
        }

        @CallSuper
        override fun getAnimationDuration(recyclerView: RecyclerView, animationType: Int,
                                          animateDx: Float, animateDy: Float): Long {
            // 手指松开，开始计算动画时间
            val duration = super.getAnimationDuration(recyclerView, animationType, animateDx, animateDy)
            //
            adapter.onSingleTapUp()

            return duration
        }

        @CallSuper
        override fun clearView(recyclerView: RecyclerView, viewHolder: ViewHolder) {
            super.clearView(recyclerView, viewHolder)
            // 拖拽完成了
            adapter.onItemComplete(viewHolder as RecyclerViewHolder)
        }

        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
            if (viewHolder.itemViewType != target.itemViewType) {
                return false
            }
            adapter.onItemDrag(viewHolder as RecyclerViewHolder, target as RecyclerViewHolder)
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            adapter.onItemSwipe(viewHolder as RecyclerViewHolder, direction)
        }
    }
}