package com.shop.products.application

import com.shop.products.domain.sortCriterion.SortCriterion
import com.shop.products.domain.sortCriterion.SortCriterionType

data class GetSortedProductsQuery(
    val salesUnitsWeight: Double,
    val stockWeight: Double,
) {
    fun toDomain(): List<SortCriterion> =
        listOf(
            SortCriterion(
                type = SortCriterionType.SALES_UNITS,
                weight = this.salesUnitsWeight,
            ),
            SortCriterion(
                type = SortCriterionType.STOCK,
                weight = this.stockWeight,
            )
        )

}
