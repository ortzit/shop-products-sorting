package com.shop.products.domain

data class Stock(
    val unitsBySize: Map<Size, Int>
)
