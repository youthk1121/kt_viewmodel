package com.example.kt_viewmodel.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.kt_viewmodel.contents.ItemDatabase
import com.example.kt_viewmodel.contents.item.ItemDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    private lateinit var itemDatabase: ItemDatabase

    @Provides
    @Singleton
    fun provideItemDatabase(@ApplicationContext appContext: Context): ItemDatabase {
        itemDatabase = Room.databaseBuilder(
            appContext,
            ItemDatabase::class.java,
            "item_database"
        ).addCallback(object: RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                itemDatabase.populateDatabase()
            }
        }).build()
        return itemDatabase
    }

    @Provides
    fun provideItemDao(database: ItemDatabase): ItemDao {
        return database.itemDao()
    }
}