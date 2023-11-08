package com.dicoding.jetcrochetstore.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.jetcrochetstore.data.CrochetRepository
import com.dicoding.jetcrochetstore.ui.screen.cart.CartViewModel
import com.dicoding.jetcrochetstore.ui.screen.detail.DetailViewModel
import com.dicoding.jetcrochetstore.ui.screen.home.HomeViewModel

class ViewModelFactory(private val repository: CrochetRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        }
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(repository) as T
        }
        if (modelClass.isAssignableFrom(CartViewModel::class.java)) {
            return CartViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}