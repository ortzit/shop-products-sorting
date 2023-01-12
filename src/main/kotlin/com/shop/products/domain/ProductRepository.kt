package com.shop.products.domain

interface ProductRepository {
    fun getAll(): List<Product>
}