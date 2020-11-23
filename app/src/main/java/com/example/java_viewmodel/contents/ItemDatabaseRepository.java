package com.example.java_viewmodel.contents;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.example.java_viewmodel.contents.item.ItemDao;
import com.example.java_viewmodel.contents.item.ItemEntity;
import com.example.java_viewmodel.mainlist.ListItemValue;
import com.example.java_viewmodel.utils.ConvertUtil;

import java.util.List;
import java.util.stream.Collectors;

public class ItemDatabaseRepository {
//    private static ItemDatabaseRepository INSTANCE;

//    private ItemDatabaseRepository(){}

//    public static ItemDatabaseRepository getInstance() {
//        if (INSTANCE != null) {
//            return INSTANCE;
//        }
//        synchronized (ItemDatabaseRepository.class) {
//            INSTANCE = new ItemDatabaseRepository();
//            return INSTANCE;
//        }
//    }

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
