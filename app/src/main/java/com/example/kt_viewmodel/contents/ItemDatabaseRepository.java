package com.example.kt_viewmodel.contents;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.example.kt_viewmodel.contents.item.ItemDao;
import com.example.kt_viewmodel.contents.item.ItemEntity;
import com.example.kt_viewmodel.mainlist.ListItemValue;
import com.example.kt_viewmodel.utils.ConvertUtil;

import java.util.List;
import java.util.stream.Collectors;

public class ItemDatabaseRepository {

    private final ItemDao mItemDao;

    public ItemDatabaseRepository(Application application) {
        ItemDatabase db = ItemDatabase.getInstance(application);
        mItemDao = db.itemDao();
    }

    public LiveData<List<ListItemValue>> getAllItemValues() {
        return Transformations.map(mItemDao.getAllItems(),
                entityList -> entityList.stream().map(ConvertUtil::convertEntityToValue).collect(Collectors.toList()));
    }

    public LiveData<List<ListItemValue>> getParticularItemValues(long minInitCount) {
        return Transformations.map(mItemDao.getParticularItems(minInitCount),
                entityList -> entityList.stream().map(ConvertUtil::convertEntityToValue).collect(Collectors.toList()));
    }

    public void insert(ItemEntity itemEntity) {
        ItemDatabase.databaseWriteExecutor.execute(() -> mItemDao.insert(itemEntity));
    }

    public void clearInsertList(List<ListItemValue> itemValueList) {
        List<ItemEntity> entityList = itemValueList.stream().map(ConvertUtil::convertValueToEntity).collect(Collectors.toList());
        ItemDatabase.databaseWriteExecutor.execute(() -> {
            mItemDao.deleteAll();
            mItemDao.insertList(entityList);
        });
    }

    public void populateItemData() {
        ItemDatabase.databaseWriteExecutor.execute(() -> ItemDatabase.populateDatabase(mItemDao));
    }
}
