package com.shop.products.infrastructure.repository

data class StockEntity(
    val productId: Int,
    val size: String,
    val units: Int,
)
