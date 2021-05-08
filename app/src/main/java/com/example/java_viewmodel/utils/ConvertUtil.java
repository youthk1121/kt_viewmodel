package com.example.java_viewmodel.utils;

import android.util.Log;

import com.example.java_viewmodel.contents.item.ItemEntity;
import com.example.java_viewmodel.mainlist.ListItemValue;

public class ConvertUtil {
    public static ListItemValue convertEntityToValue(ItemEntity itemEntity) {
        Log.d("convertEntityToValue", String.format("id=%d,name=%s,number=%s,initCount=%d,currentCount=%d",itemEntity.id, itemEntity.name, itemEntity.number, itemEntity.initCount, itemEntity.currentCount));
        return new ListItemValue(itemEntity.id, itemEntity.name, itemEntity.number, itemEntity.initCount, itemEntity.currentCount);
    }

    public static ItemEntity convertValueToEntity(ListItemValue value) {
        return new ItemEntity(value.getId(), value.getName(), value.getNumber(), value.getInitCount(), value.getCurrentCount());
    }
}
