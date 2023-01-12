package com.shop.products.domain.sortCriterion

data class SortCriterion(
    val type: SortCriterionType,
    val weight: Double,
)

enum class SortCriterionType {
    SALES_UNITS, STOCK
}
