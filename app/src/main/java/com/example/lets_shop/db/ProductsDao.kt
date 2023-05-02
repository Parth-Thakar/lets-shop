package com.example.lets_shop.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.lets_shop.models.Product


@Dao
interface ProductsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addProducts(list : List<Product>)

    @Query("SELECT * FROM Product")
    suspend fun getProducts() : List<Product>


}