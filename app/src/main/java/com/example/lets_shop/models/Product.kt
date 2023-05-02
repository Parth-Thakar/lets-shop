package com.example.lets_shop.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

// data class of the product which is the entity class for the roomDB too.

@Entity
data class Product(
    val category: String,
    val description: String,
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val image: String,
    val price: Double,
    val title: String
) : Serializable