package com.varani.keyvaluestore.di

import com.varani.keyvaluestore.data.CacheRepository
import com.varani.keyvaluestore.data.KeyValueStoreRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
interface DataModule {

    @Singleton
    @Binds
    fun bindsKeyValueRepository(cacheRepository: CacheRepository): KeyValueStoreRepository
}