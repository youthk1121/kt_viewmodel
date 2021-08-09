package com.example.kt_viewmodel.di

import com.example.kt_viewmodel.contents.ItemDatabaseRepository
import com.example.kt_viewmodel.contents.ItemDatabaseRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindItemDatabaseRepository(impl: ItemDatabaseRepositoryImpl): ItemDatabaseRepository
}