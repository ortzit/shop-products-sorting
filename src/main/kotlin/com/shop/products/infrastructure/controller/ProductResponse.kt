package com.shop.products.infrastructure.controller

data class ProductResponse(
    val id: Int,
    val name: String,
    val salesUnits: Int,
    val stock: Map<String, Int>,
)