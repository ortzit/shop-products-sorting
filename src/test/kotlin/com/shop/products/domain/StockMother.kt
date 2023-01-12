package com.shop.products.domain

import com.shop.products.anyAbsoluteValueInt

fun anyStock(): Stock =
    Stock(anyUnitsBySize())

private fun anyUnitsBySize(): Map<Size, Int> =
    Size.values().associateWith { anyAbsoluteValueInt() }