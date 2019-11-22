package com.allco.ui.bottomsheet.utils

import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableArrayList
import androidx.databinding.ViewDataBinding
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ObservableList
import com.allco.ui.bottomsheet.BR

open class ObserverBasedAdapter constructor(private val data: ItemList) :
    RecyclerView.Adapter<ObserverBasedAdapter.ViewHolder>() {

    class ItemList : ObservableArrayList<Item>()

    interface Item {
        @get:LayoutRes
        val layout: Int
        val binder: ((ViewDataBinding, Int) -> Unit)
            get() = { binding, _ ->
                binding.setVariable(BR.model, this)
            }
    }

    init {
        data.addOnListChangedCallback(object : ObservableList.OnListChangedCallback<ItemList>() {
            override fun onChanged(sender: ItemList?) = notifyDataSetChanged()
            override fun onItemRangeRemoved(sender: ItemList?, positionStart: Int, itemCount: Int) = notifyItemRangeRemoved(positionStart, itemCount)
            override fun onItemRangeMoved(sender: ItemList?, fromPosition: Int, toPosition: Int, itemCount: Int) = notifyDataSetChanged()
            override fun onItemRangeInserted(sender: ItemList?, positionStart: Int, itemCount: Int) = notifyItemRangeInserted(positionStart, itemCount)
            override fun onItemRangeChanged(sender: ItemList?, positionStart: Int, itemCount: Int) = notifyItemRangeChanged(positionStart, itemCount)
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), viewType, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        data[position].binder(holder.binding, position)
    }

    override fun getItemCount() = data.size

    override fun getItemViewType(position: Int) = data[position].layout

    class ViewHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root)
}
