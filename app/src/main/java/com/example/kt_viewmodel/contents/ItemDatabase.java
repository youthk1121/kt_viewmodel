package com.example.kt_viewmodel.contents;

import android.util.Log;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.kt_viewmodel.contents.item.ItemDao;
import com.example.kt_viewmodel.contents.item.ItemEntity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {ItemEntity.class}, version = 1, exportSchema = false)
public abstract class ItemDatabase extends RoomDatabase {

    public abstract ItemDao itemDao();

    public void populateDatabase() {
        Log.d("ItemDatabase", "is called populateDatabase");
        databaseWriteExecutor.execute(() -> {
            ItemDao itemDao = itemDao();
            itemDao.deleteAll();
            ItemEntity item1 = new ItemEntity("name1", "1", 1, 1);
            itemDao.insert(item1);
            ItemEntity item2 = new ItemEntity("name2", "2", 2, 2);
            itemDao.insert(item2);
            ItemEntity item3 = new ItemEntity("name3", "3", 3, 3);
            itemDao.insert(item3);
            ItemEntity item4 = new ItemEntity("name4", "4", 4, 4);
            itemDao.insert(item4);
            ItemEntity item5 = new ItemEntity("name5", "5", 5, 5);
            itemDao.insert(item5);
            ItemEntity item6 = new ItemEntity("name6", "6", 6, 6);
            itemDao.insert(item6);
            ItemEntity item7 = new ItemEntity("name7", "7", 7, 7);
            itemDao.insert(item7);
            ItemEntity item8 = new ItemEntity("name8", "8", 8, 8);
            itemDao.insert(item8);
            ItemEntity item9 = new ItemEntity("name9", "9", 9, 9);
            itemDao.insert(item9);
            ItemEntity item10 = new ItemEntity("name10", "10", 10, 10);
            itemDao.insert(item10);
        });
    }

    public static final ExecutorService databaseWriteExecutor = Executors.newCachedThreadPool();

}
