package com.example.kt_viewmodel.contents.item

import androidx.room.Dao
import androidx.lifecycle.LiveData
import androidx.room.Insert
import com.example.kt_viewmodel.contents.item.ItemEntity
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ItemDao {
    @Query("SELECT * FROM item_t")
    fun getAllItems(): LiveData<List<ItemEntity>>

    @Query("SELECT * FROM item_t WHERE init_count >= :minInitCount  ORDER BY number ASC")
    fun getParticularItems(minInitCount: Long): LiveData<List<ItemEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(entity: ItemEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertList(entityList: List<ItemEntity>)

    @Query("DELETE FROM item_t")
    suspend fun deleteAll()
}