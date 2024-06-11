package com.example.basic_weather_forecast.di

import android.content.Context
import com.example.basic_weather_forecast.datastore.room.InventoryDatabase
import com.example.basic_weather_forecast.datastore.room.ItemDao
import com.example.basic_weather_forecast.datastore.room.ItemsRepository
import com.example.basic_weather_forecast.datastore.room.OfflineItemsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RoomDatabaseModule {

    @Provides
    fun provideItemDao(inventoryDatabase: InventoryDatabase): ItemDao {
        return inventoryDatabase.itemDao()
    }

    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context): InventoryDatabase {
        return InventoryDatabase.getDatabase(appContext)
    }

    @Provides
    fun provideOfflineItemsRepository(itemDao: ItemDao): ItemsRepository {
        return OfflineItemsRepository(itemDao)
    }
}