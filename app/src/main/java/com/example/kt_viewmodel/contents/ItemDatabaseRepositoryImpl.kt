package com.example.kt_viewmodel.contents

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.kt_viewmodel.contents.item.ItemDao
import com.example.kt_viewmodel.contents.item.ItemEntity
import com.example.kt_viewmodel.di.IoDispatcher
import com.example.kt_viewmodel.mainlist.ListItemValue
import com.example.kt_viewmodel.utils.ConvertUtil
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ItemDatabaseRepositoryImpl @Inject constructor(
    private val itemDatabase: ItemDatabase,
    private val itemDao: ItemDao,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
    ): ItemDatabaseRepository {

    override fun getAllItemValues(): LiveData<List<ListItemValue>> {
        return Transformations.map(itemDao.getAllItems()) { entityList: List<ItemEntity> -> entityList.map { obj: ItemEntity -> ConvertUtil.convertEntityToValue(obj) } }
    }

    override fun getParticularItemValues(minInitCount: Long): LiveData<List<ListItemValue>> {
        return Transformations.map(itemDao.getParticularItems(minInitCount)) { entityList: List<ItemEntity> -> entityList.map { obj: ItemEntity -> ConvertUtil.convertEntityToValue(obj) } }
    }

    override suspend fun insert(listItemValue: ListItemValue) {
        withContext(dispatcher) {
            itemDao.insert(ConvertUtil.convertValueToEntity(listItemValue))
        }
    }

    override suspend fun clearInsertList(itemValueList: List<ListItemValue>?) {
        val entityList: List<ItemEntity> = itemValueList?.map { obj: ListItemValue -> ConvertUtil.convertValueToEntity(obj) } ?: emptyList()
        withContext(dispatcher) {
            itemDao.deleteAll()
            itemDao.insertList(entityList)
        }
    }

    override suspend fun populateItemData() {
        withContext(dispatcher) {
            itemDatabase.populateDatabase()
        }
    }
}