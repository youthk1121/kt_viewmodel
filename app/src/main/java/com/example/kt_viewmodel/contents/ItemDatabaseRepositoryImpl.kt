package com.example.kt_viewmodel.contents

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.kt_viewmodel.contents.item.ItemDao
import com.example.kt_viewmodel.contents.item.ItemEntity
import com.example.kt_viewmodel.mainlist.ListItemValue
import com.example.kt_viewmodel.utils.ConvertUtil
import javax.inject.Inject

class ItemDatabaseRepositoryImpl @Inject constructor(
    private val itemDatabase: ItemDatabase,
    private val itemDao: ItemDao
    ): ItemDatabaseRepository {

    override fun getAllItemValues(): LiveData<List<ListItemValue>> {
        return Transformations.map(itemDao.allItems) { entityList: List<ItemEntity> -> entityList.map { obj: ItemEntity -> ConvertUtil.convertEntityToValue(obj) } }
    }

    override fun getParticularItemValues(minInitCount: Long): LiveData<List<ListItemValue>> {
        return Transformations.map(itemDao.getParticularItems(minInitCount)) { entityList: List<ItemEntity> -> entityList.map { obj: ItemEntity -> ConvertUtil.convertEntityToValue(obj) } }
    }

    override fun insert(itemEntity: ItemEntity?) {
        ItemDatabase.databaseWriteExecutor.execute { itemDao.insert(itemEntity) }
    }

    override fun clearInsertList(itemValueList: List<ListItemValue>?) {
        val entityList: List<ItemEntity> = itemValueList?.map { obj: ListItemValue -> ConvertUtil.convertValueToEntity(obj) } ?: emptyList()
        ItemDatabase.databaseWriteExecutor.execute {
            itemDao.deleteAll()
            itemDao.insertList(entityList)
        }
    }

    override fun populateItemData() {
        itemDatabase.populateDatabase()
    }
}