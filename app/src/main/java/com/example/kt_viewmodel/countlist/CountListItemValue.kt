package com.example.kt_viewmodel.countlist

import androidx.lifecycle.MutableLiveData

class CountListItemValue(var name: String, var number: String, var initCount: Long) {
    val currentCountLiveData = MutableLiveData(0L)
    var currentCount: Long
        get() = currentCountLiveData.value ?: 0
        set(currentCountLiveData) {
            this.currentCountLiveData.value = currentCountLiveData
        }
}