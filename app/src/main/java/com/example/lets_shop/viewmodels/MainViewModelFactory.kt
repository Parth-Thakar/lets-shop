package com.example.lets_shop.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.lets_shop.repository.ProductRepository
import com.example.lets_shop.viewmodels.MainViewModel
import javax.inject.Inject

class MainViewModelFactory @Inject constructor(private val repository: ProductRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(repository) as T
    }

}