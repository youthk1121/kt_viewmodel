package com.example.kt_viewmodel.mainlist

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.kt_viewmodel.contents.ItemDatabaseRepository

class ListViewModel(application: Application) : AndroidViewModel(application) {
    private val itemDatabaseRepository: ItemDatabaseRepository = ItemDatabaseRepository(application)
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
    } //    public static class ListViewModelFactory implements ViewModelProvider.Factory {

    //
    //        @NonNull
    //        private final Application application;
    //
    //        private final long minInitCount;
    //
    //        public ListViewModelFactory(@NonNull Application application, long minInitCount) {
    //            this.application = application;
    //            this.minInitCount = minInitCount;
    //        }
    //
    //        @SuppressWarnings("unchecked")
    //        @NonNull
    //        @Override
    //        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
    //            if (modelClass == ListViewModel.class) {
    //                return (T) new ListViewModel(application, minInitCount);
    //            } else {
    //                return null;
    //            }
    //        }
    //    }
    init {
        val initItemValues = itemDatabaseRepository.allItemValues
        initItemValues.observeForever { initValueList: List<ListItemValue> ->
            itemList = initValueList
            listMutableLiveData.postValue(initValueList)
        }
    }
}