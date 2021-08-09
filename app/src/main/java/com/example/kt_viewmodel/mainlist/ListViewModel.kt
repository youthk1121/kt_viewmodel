package com.example.kt_viewmodel.mainlist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.kt_viewmodel.contents.ItemDatabaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val itemDatabaseRepository: ItemDatabaseRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _itemList = itemDatabaseRepository.getAllItemValues()
    val itemList: LiveData<List<ListItemValue>>
        get() = _itemList

    fun getItemIndex(number: String): Int {
        val list = _itemList.value ?: return -1
        for (i in list.indices) {
            if (number == list[i].number) {
                return i
            }
        }
        return -1
    }

    fun decreaseItemCount(index: Int, count: Int): Boolean {
        val list = _itemList.value ?: return false
        val item = list[index]
        if (item.currentCount < count) {
            return false
        }
        item.currentCount = item.currentCount - count
        return true
    }

    fun populateData() {
        itemDatabaseRepository.populateItemData()
    }

    fun saveList() {
        Log.d("ListViewModel", "numbers=" + _itemList.value?.joinToString(",") { o -> o.currentCount.toString() })
        itemDatabaseRepository.clearInsertList(_itemList.value)
    }
}