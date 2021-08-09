package com.example.kt_viewmodel.contents

import android.util.Log
import androidx.room.Database
import com.example.kt_viewmodel.contents.item.ItemEntity
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.kt_viewmodel.contents.item.ItemDao
import com.example.kt_viewmodel.contents.ItemDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.concurrent.Executors

@Database(entities = [ItemEntity::class], version = 1, exportSchema = false)
abstract class ItemDatabase : RoomDatabase() {

    abstract fun itemDao(): ItemDao

    suspend fun populateDatabase() {
        val itemDao = itemDao()
        itemDao.deleteAll()
        val item1 = ItemEntity("name1", "1", 1, 1)
        itemDao.insert(item1)
        val item2 = ItemEntity("name2", "2", 2, 2)
        itemDao.insert(item2)
        val item3 = ItemEntity("name3", "3", 3, 3)
        itemDao.insert(item3)
        val item4 = ItemEntity("name4", "4", 4, 4)
        itemDao.insert(item4)
        val item5 = ItemEntity("name5", "5", 5, 5)
        itemDao.insert(item5)
        val item6 = ItemEntity("name6", "6", 6, 6)
        itemDao.insert(item6)
        val item7 = ItemEntity("name7", "7", 7, 7)
        itemDao.insert(item7)
        val item8 = ItemEntity("name8", "8", 8, 8)
        itemDao.insert(item8)
        val item9 = ItemEntity("name9", "9", 9, 9)
        itemDao.insert(item9)
        val item10 = ItemEntity("name10", "10", 10, 10)
        itemDao.insert(item10)
    }
}