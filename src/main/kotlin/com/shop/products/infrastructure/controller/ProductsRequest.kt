package com.shop.products.infrastructure.controller

data class ProductsRequest(
    val sortingSalesUnitsWeight: Double = 0.0,
    val sortingStockWeight: Double = 0.0,
)
