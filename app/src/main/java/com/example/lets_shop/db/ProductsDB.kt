package com.example.lets_shop.db

import androidx.room.Database
import androidx.room.RoomDatabase

import com.example.lets_shop.models.Product

@Database(entities = [Product::class], version = 1)
abstract class ProductsDB : RoomDatabase() {

    abstract fun getProductDao() : ProductsDao


}