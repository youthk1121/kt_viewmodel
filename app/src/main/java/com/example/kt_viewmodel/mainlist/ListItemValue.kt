package com.example.kt_viewmodel.mainlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class ListItemValue(val id: Long, var name: String, var number: String, var initCount: Long, currentCount: Long) {
    private val currentCountLiveData = MutableLiveData<Long>()
    var currentCount: Long
        get() = currentCountLiveData.value ?: 0
        set(currentCountLiveData) {
            this.currentCountLiveData.value = currentCountLiveData
        }

    fun getCurrentCountLiveData(): LiveData<Long> {
        return currentCountLiveData
    }

    init {
        currentCountLiveData.value = currentCount
    }
}