package com.dicoding.jetcrochetstore.di

import com.dicoding.jetcrochetstore.data.CrochetRepository

object Injection {
    fun provideRepository(): CrochetRepository {
        return CrochetRepository.getInstance()
    }
}