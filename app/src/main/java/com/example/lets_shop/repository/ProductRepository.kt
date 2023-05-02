package com.example.lets_shop.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.lets_shop.db.ProductsDB
import com.example.lets_shop.models.Product
import com.example.lets_shop.retrofit.ApiInterface
import com.example.lets_shop.utils.NetworkUtils
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val apiInterface: ApiInterface,
    private val productsDB: ProductsDB,
    private val applicationContext: Context
) {


    private val productLiveData = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>>
        get() = productLiveData

    suspend fun getProducts() {

        if (NetworkUtils.isInternetAvailable(applicationContext))
        {
            val result = apiInterface.getProducts()
            if (result.isSuccessful && result.body() != null) {
                productsDB.getProductDao().addProducts(result.body()!!)
                productLiveData.postValue(result.body())
            }
        }
        else{
            val products = productsDB.getProductDao().getProducts()
            productLiveData.postValue(products)
        }

    }

}