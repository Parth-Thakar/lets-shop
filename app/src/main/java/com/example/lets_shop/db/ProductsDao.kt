package com.example.lets_shop.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.lets_shop.models.Product

// Dao Interface of RoomDB
@Dao
interface ProductsDao {

    //Insert function to insert the list of products inside the roomDB
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addProducts(list : List<Product>)

    // Get function to return the list of products from the Entity Product.
    @Query("SELECT * FROM Product")
    suspend fun getProducts() : List<Product>


}