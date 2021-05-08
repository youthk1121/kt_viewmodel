package com.example.kt_viewmodel.countlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.kt_viewmodel.R
import java.util.*

/**
 * [RecyclerView.Adapter]
 */
class CountListRecyclerAdapter(private val viewLifecycleOwner: LifecycleOwner, private val countListViewModel: CountListViewModel) : RecyclerView.Adapter<CountListRecyclerAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_count_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mItem = countListViewModel.itemList?.get(position)
        holder.mNameView.text = countListViewModel.itemList?.get(position)?.name
        holder.mInitCountView.text = String.format(Locale.getDefault(), "%d", countListViewModel.itemList?.get(position)?.initCount)
        countListViewModel.itemList?.get(position)?.currentCountLiveData?.observe(viewLifecycleOwner, holder.observer)
        holder.mPlusButton.setOnClickListener { v: View ->
            if (!countListViewModel.addItemCurrentCount(position, 1)) {
                Toast.makeText(v.context, "これ以上増やせません", Toast.LENGTH_SHORT).show()
            }
        }
        holder.mMinusButton.setOnClickListener { v: View ->
            if (!countListViewModel.decreaseItemCurrentCount(position, 1)) {
                Toast.makeText(v.context, "これ以上減らせません", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun getItemCount(): Int {
        return countListViewModel.itemList!!.size
    }

    class ViewHolder(mView: View) : RecyclerView.ViewHolder(mView) {
        val mNameView: TextView = mView.findViewById(R.id.item_name)
        val mInitCountView: TextView = mView.findViewById(R.id.item_init_count)
        private val mCurrentCountView: TextView = mView.findViewById(R.id.item_current_count)
        val mPlusButton: Button = mView.findViewById(R.id.plus_button)
        val mMinusButton: Button = mView.findViewById(R.id.minus_button)
        var mItem: CountListItemValue? = null
        val observer: Observer<Long> = Observer { aLong -> mCurrentCountView.text = String.format(Locale.getDefault(), "%d", aLong) }
        override fun toString(): String {
            return super.toString() + " '" + mCurrentCountView.text + "'"
        }
    }
}