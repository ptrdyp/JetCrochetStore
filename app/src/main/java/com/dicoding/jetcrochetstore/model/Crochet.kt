package com.dicoding.jetcrochetstore.model

data class Crochet (
    val id: Long,
    val image: String,
    val title: String,
    val category: String,
    val description: String,
    val price: Int,
)