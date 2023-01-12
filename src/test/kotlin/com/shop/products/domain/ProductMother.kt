package com.shop.products.domain

import com.shop.products.anyAbsoluteValueInt
import com.shop.products.anyPositiveInt
import com.shop.products.anyString

fun anyProduct(): Product =
    Product(
        id = anyProductId(),
        name = anyProductName(),
        salesUnits = anyProductSalesUnits(),
        stock = anyStock()
    )

private fun anyProductId(): ProductId = ProductId(anyProductIdValue())

private fun anyProductName(): String = anyString()

private fun anyProductSalesUnits(): Int = anyAbsoluteValueInt()

private fun anyProductIdValue(): Int = anyPositiveInt()
