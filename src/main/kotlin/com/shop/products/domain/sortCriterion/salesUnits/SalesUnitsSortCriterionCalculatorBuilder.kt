package com.shop.products.domain.sortCriterion.salesUnits

import com.shop.products.domain.Product
import com.shop.products.domain.sortCriterion.SortCriterion
import com.shop.products.domain.sortCriterion.SortCriterionCalculator
import com.shop.products.domain.sortCriterion.SortCriterionCalculatorBuilder
import com.shop.products.domain.sortCriterion.SortCriterionType
import org.springframework.stereotype.Service

@Service
class SalesUnitsSortCriterionCalculatorBuilder : SortCriterionCalculatorBuilder {
    override fun build(sortCriterion: SortCriterion): SortCriterionCalculator? =
        sortCriterion
            .takeIf { it.type == SortCriterionType.SALES_UNITS }
            ?.let {
                SalesUnitsSortCriterionCalculator(it.weight)
            }

    private class SalesUnitsSortCriterionCalculator(private val weight: Double) : SortCriterionCalculator {
        override fun weight(product: Product): Double =
            product.salesUnits * weight
    }
}