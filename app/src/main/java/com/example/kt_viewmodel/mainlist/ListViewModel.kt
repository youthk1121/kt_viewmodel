package com.example.kt_viewmodel.mainlist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
    private var itemList: List<ListItemValue>? = null
    private val listMutableLiveData = MutableLiveData<List<ListItemValue>>()
    fun setItemList(itemList: List<ListItemValue>) {
        this.itemList = itemList
        listMutableLiveData.value = itemList
    }

    val isEmpty: Boolean
        get() = itemList?.isEmpty() ?:false

    val liveData: LiveData<List<ListItemValue>>
        get() = listMutableLiveData

    fun getItemIndex(number: String): Int {
        if (itemList == null) {
            return -1
        }
        for (i in itemList!!.indices) {
            if (number == itemList!![i].number) {
                return i
            }
        }
        return -1
    }

    fun decreaseItemCount(index: Int, count: Int): Boolean {
        if (itemList == null) {
            return false
        }
        val item = itemList!![index]
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
        Log.d("ListViewModel", "numbers=" + listMutableLiveData.value?.joinToString(",") { o: ListItemValue -> o.currentCount.toString() })
        itemDatabaseRepository.clearInsertList(listMutableLiveData.value)
    }

    init {
        val initItemValues = itemDatabaseRepository.getAllItemValues()
        initItemValues.observeForever { initValueList: List<ListItemValue> ->
            itemList = initValueList
            listMutableLiveData.postValue(initValueList)
        }
    }
}