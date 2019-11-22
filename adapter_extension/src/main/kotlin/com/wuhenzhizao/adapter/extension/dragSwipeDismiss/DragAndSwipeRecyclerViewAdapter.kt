package com.wuhenzhizao.adapter.extension.dragSwipeDismiss

import android.content.Context
import android.view.MotionEvent
import android.view.View
import com.wuhenzhizao.adapter.RecyclerViewAdapter
import com.wuhenzhizao.adapter.extension.getItems
import com.wuhenzhizao.adapter.extension.removeItemAt
import com.wuhenzhizao.adapter.holder.RecyclerViewHolder
import com.wuhenzhizao.adapter.interfaces.Listener
import java.util.*

/**
 * Created by liufei on 2017/12/4.
 */
open class DragAndSwipeRecyclerViewAdapter<T : Any>(context: Context, items: List<T>?) : RecyclerViewAdapter<T>(context, items), DragAndDismissInterface<RecyclerViewHolder> {
    internal var innerDrawListener: ItemDrawListener<RecyclerViewHolder>? = null
    internal var innerGestureListener: ItemGestureListener<RecyclerViewHolder>? = null
    internal var innerMovingListener: ItemMovingListener<RecyclerViewHolder>? = null
    internal var innerCompleteListener: ItemCompleteListener<RecyclerViewHolder>? = null
    internal var innerDragListener: ItemDragListener<RecyclerViewHolder>? = null
    internal var innerSwipeListener: ItemSwipeListener<RecyclerViewHolder>? = null

    constructor(context: Context) : this(context, null)

    override fun onChildDraw(target: RecyclerViewHolder, dX: Float, dY: Float) {
        innerDrawListener?.run {
            onChildDraw(target.itemView, target.adapterPosition, dX, dY)
        }
    }

    override fun onSingleTapUp() {
        innerGestureListener?.run {
            onSingleTapUp()
        }
    }

    override fun onItemMoving(target: RecyclerViewHolder?, actionState: Int) {
        target?.run {
            val targetPosition = this.adapterPosition
            innerMovingListener?.run {
                onItemMoving(targetPosition, actionState)
            }
        }
    }

    override fun onItemComplete(target: RecyclerViewHolder) {
        val targetPosition = target.adapterPosition
        innerCompleteListener?.apply {
            onItemComplete(targetPosition)
        }
    }

    override fun onItemDrag(from: RecyclerViewHolder, target: RecyclerViewHolder) {
        val fromPosition = from.adapterPosition
        val targetPosition = target.adapterPosition
        Collections.swap(getItems(), fromPosition, targetPosition)
        super.notifyItemMoved(fromPosition, targetPosition)
        innerDragListener?.apply {
            onItemDrag(fromPosition, targetPosition)
        }
    }

    override fun onItemSwipe(holder: RecyclerViewHolder, direction: Int) {
        removeItemAt(holder.adapterPosition)
        val position = holder.layoutPosition
        innerSwipeListener?.apply {
            onItemSwipe(position, direction)
        }
    }

    override fun setListener(listener: Listener<RecyclerViewHolder>) {
        super.setListener(listener)
        when (listener) {
            is ItemDrawListener -> innerDrawListener = listener
            is ItemGestureListener -> innerGestureListener = listener
            is ItemMovingListener -> innerMovingListener = listener
            is ItemCompleteListener -> innerCompleteListener = listener
            is ItemDragListener -> innerDragListener = listener
            is ItemSwipeListener -> innerSwipeListener = listener
        }
    }
}

/**
 * 监听recyclerView item moving 操作
 */
inline fun <T : Any, Adapter : DragAndSwipeRecyclerViewAdapter<T>> Adapter.movingListener(crossinline block: (target: Int, actionState: Int) -> Unit): Adapter {
    setListener(object : ItemMovingListener<RecyclerViewHolder> {
        override fun onItemMoving(target: Int, actionState: Int) {
            block(target, actionState)
        }
    })
    return this
}

/**
 * 监听recyclerView item draw 操作
 */
inline fun <T : Any, Adapter : DragAndSwipeRecyclerViewAdapter<T>> Adapter.drawListener(crossinline block: (view: View, target: Int, dX: Float, dY: Float) -> Unit): Adapter {
    setListener(object : ItemDrawListener<RecyclerViewHolder> {
        override fun onChildDraw(view: View, target: Int, dX: Float, dY: Float) {
            block(view, target, dX, dY)
        }
    })
    return this
}

/**
 * 监听recyclerView item 手指松开 操作
 */
inline fun <T : Any, Adapter : DragAndSwipeRecyclerViewAdapter<T>> Adapter.gestureListener(crossinline block: (event: Int) -> Unit): Adapter {
    setListener(object : ItemGestureListener<RecyclerViewHolder> {
        override fun onSingleTapUp() {
            block(MotionEvent.ACTION_UP)
        }
    })
    return this
}

/**
 * 监听recyclerView item 完成时的 操作
 */
inline fun <T : Any, Adapter : DragAndSwipeRecyclerViewAdapter<T>> Adapter.completeListener(crossinline block: (target: Int) -> Unit): Adapter {
    setListener(object : ItemCompleteListener<RecyclerViewHolder> {
        override fun onItemComplete(target: Int) {
            block(target)
        }
    })
    return this
}

/**
 * 监听recyclerView item drag & drop 操作
 */
inline fun <T : Any, Adapter : DragAndSwipeRecyclerViewAdapter<T>> Adapter.dragListener(crossinline block: (from: Int, target: Int) -> Unit): Adapter {
    setListener(object : ItemDragListener<RecyclerViewHolder> {
        override fun onItemDrag(from: Int, target: Int) {
            block(from, target)
        }
    })
    return this
}

/**
 * 监听recyclerView item swipe dismiss 操作
 */
inline fun <T : Any, Adapter : DragAndSwipeRecyclerViewAdapter<T>> Adapter.swipeListener(crossinline block: (position: Int, direction: Int) -> Unit): Adapter {
    setListener(object : ItemSwipeListener<RecyclerViewHolder> {
        override fun onItemSwipe(position: Int, direction: Int) {
            block(position, direction)
        }
    })
    return this
}