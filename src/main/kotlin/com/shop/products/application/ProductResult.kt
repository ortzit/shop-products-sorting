package com.shop.products.application

data class ProductResult(
    val id: Int,
    val name: String,
    val salesUnits: Int,
    val stock: Map<String, Int>,
)
