package com.example.kt_viewmodel.contents

import androidx.lifecycle.LiveData
import com.example.kt_viewmodel.contents.item.ItemEntity
import com.example.kt_viewmodel.mainlist.ListItemValue

interface ItemDatabaseRepository {

    fun getAllItemValues(): LiveData<List<ListItemValue>>

    fun getParticularItemValues(minInitCount: Long): LiveData<List<ListItemValue>>

    suspend fun insert(listItemValue: ListItemValue)

    suspend fun clearInsertList(itemValueList: List<ListItemValue>?)

    suspend fun populateItemData()
}