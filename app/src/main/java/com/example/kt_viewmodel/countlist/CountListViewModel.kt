package com.example.kt_viewmodel.countlist

import androidx.lifecycle.ViewModel

class CountListViewModel : ViewModel() {
    var itemList: List<CountListItemValue>? = null
    fun addItemCurrentCount(index: Int, addCount: Int): Boolean {
        val item = itemList!![index]
        if (item.currentCount + addCount > item.initCount) {
            return false
        }
        item.currentCount = item.currentCount + addCount
        return true
    }

    fun decreaseItemCurrentCount(index: Int, count: Int): Boolean {
        val item = itemList!![index]
        if (count > item.currentCount) {
            return false
        }
        item.currentCount = item.currentCount - count
        return true
    }
}