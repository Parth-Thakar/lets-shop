package com.example.lets_shop.di

import android.content.Context
import androidx.room.Room
import com.example.lets_shop.db.ProductsDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideProductDB(context : Context) : ProductsDB {
        return Room.databaseBuilder(context, ProductsDB::class.java,"ProductDB").build()
    }

}