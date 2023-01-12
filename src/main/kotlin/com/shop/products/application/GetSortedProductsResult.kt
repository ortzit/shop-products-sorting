package com.shop.products.application

import com.shop.products.domain.Product

data class GetSortedProductsResult(val products: List<ProductResult>) {
    companion object {
        fun fromDomain(products: List<Product>): GetSortedProductsResult =
            GetSortedProductsResult(
                products.map { product ->
                    ProductResult(
                        id = product.id.value,
                        name = product.name,
                        salesUnits = product.salesUnits,
                        stock = product.stock.unitsBySize.mapKeys { it.key.name }
                    )
                }
            )
    }
}
