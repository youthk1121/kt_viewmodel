package com.example.kt_viewmodel.utils

import android.util.Log
import com.example.kt_viewmodel.contents.item.ItemEntity
import com.example.kt_viewmodel.mainlist.ListItemValue

object ConvertUtil {
    @JvmStatic
    fun convertEntityToValue(itemEntity: ItemEntity): ListItemValue {
        Log.d("convertEntityToValue", String.format("id=%d,name=%s,number=%s,initCount=%d,currentCount=%d", itemEntity.id, itemEntity.name, itemEntity.number, itemEntity.initCount, itemEntity.currentCount))
        return ListItemValue(itemEntity.id, itemEntity.name, itemEntity.number, itemEntity.initCount, itemEntity.currentCount)
    }

    @JvmStatic
    fun convertValueToEntity(value: ListItemValue): ItemEntity {
        return ItemEntity(value.id, value.name, value.number, value.initCount, value.currentCount)
    }
}