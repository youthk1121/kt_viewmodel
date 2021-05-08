package com.example.java_viewmodel.contents.item;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ItemDao {
    @Query("SELECT * FROM item_t")
    LiveData<List<ItemEntity>> getAllItems();

    @Query("SELECT * FROM item_t WHERE init_count >= :minInitCount  ORDER BY number ASC")
    LiveData<List<ItemEntity>> getParticularItems(long minInitCount);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(ItemEntity entity);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertList(List<ItemEntity> entityList);

    @Query("DELETE FROM item_t")
    void deleteAll();
}
