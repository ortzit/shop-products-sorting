package com.shop.products.infrastructure.repository

import com.shop.products.domain.Product
import com.shop.products.domain.ProductId
import com.shop.products.domain.Size
import com.shop.products.domain.Stock

data class ProductEntity(
    val id: Int,
    val name: String,
    val salesUnits: Int,
    val stockList: ArrayList<StockEntity>
) {
    fun toDomain(): Product =
        Product(
            id = ProductId(this.id),
            name = this.name,
            salesUnits = this.salesUnits,
            stock = Stock(
                this.stockList.associate { stockEntity ->
                    Size.valueOf(stockEntity.size) to stockEntity.units
                }
            )
        )
}
