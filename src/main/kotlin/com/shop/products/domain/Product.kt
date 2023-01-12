package com.shop.products.domain

data class Product(
    val id: ProductId,
    val name: String,
    val salesUnits: Int,
    val stock: Stock
)

data class ProductId(val value: Int)