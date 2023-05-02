package com.example.lets_shop.retrofit


import com.example.lets_shop.models.Product
import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {

    // Api Interface that have the function getProducts and intialized by the GET Method of HTTP requests.
    // Having end point of API BASE_URL.
    // Returns the List of Product in Retrofit Response format.
    @GET("products")
    suspend fun getProducts() : Response<List<Product>>

}