package com.example.kt_viewmodel.mainlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.kt_viewmodel.R
import java.util.*

/**
 * [RecyclerView.Adapter]
 */
class ItemRecyclerViewAdapter(private val viewLifecycleOwner: LifecycleOwner) : ListAdapter<ListItemValue, ItemRecyclerViewAdapter.ViewHolder>(itemCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mItem = getItem(position)
        holder.mNameView.text = getItem(position)?.name
        getItem(position)?.getCurrentCountLiveData()?.observe(viewLifecycleOwner, holder.observer)
    }

    class ViewHolder(mView: View) : RecyclerView.ViewHolder(mView) {
        val mNameView: TextView = mView.findViewById(R.id.item_name)
        private val mCountView: TextView = mView.findViewById(R.id.item_count)
        var mItem: ListItemValue? = null
        val observer: Observer<Long> = Observer { aLong -> mCountView.text = String.format(Locale.getDefault(), "%d", aLong) }
        override fun toString(): String {
            return super.toString() + " '" + mCountView.text + "'"
        }
    }

    companion object {
        private val itemCallback: DiffUtil.ItemCallback<ListItemValue>
            get() = object : DiffUtil.ItemCallback<ListItemValue>() {
                override fun areItemsTheSame(oldItem: ListItemValue, newItem: ListItemValue): Boolean {
                    return oldItem == newItem
                }

                override fun areContentsTheSame(oldItem: ListItemValue, newItem: ListItemValue): Boolean {
                    return oldItem.number == newItem.number
                }
            }
    }
}