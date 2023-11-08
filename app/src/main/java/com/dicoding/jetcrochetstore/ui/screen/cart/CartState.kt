package com.dicoding.jetcrochetstore.ui.screen.cart

import com.dicoding.jetcrochetstore.model.OrderCrochet

data class CartState (
    val orderCrochet: List<OrderCrochet>,
    val totalPrice: Int
)