package com.example.lets_shop.retrofit


import com.example.lets_shop.models.Product
import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {

    @GET("products")
    suspend fun getProducts() : Response<List<Product>>

}