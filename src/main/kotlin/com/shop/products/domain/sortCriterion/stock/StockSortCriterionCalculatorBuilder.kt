package com.shop.products.domain.sortCriterion.stock

import com.shop.products.domain.Product
import com.shop.products.domain.sortCriterion.SortCriterion
import com.shop.products.domain.sortCriterion.SortCriterionCalculator
import com.shop.products.domain.sortCriterion.SortCriterionCalculatorBuilder
import com.shop.products.domain.sortCriterion.SortCriterionType
import org.springframework.stereotype.Service

@Service
class StockCriterionCalculatorBuilder : SortCriterionCalculatorBuilder {
    override fun build(sortCriterion: SortCriterion): SortCriterionCalculator? =
        sortCriterion
            .takeIf { it.type == SortCriterionType.STOCK }
            ?.let {
                StockCriterionCalculator(it.weight)
            }

    private class StockCriterionCalculator(private val weight: Double) : SortCriterionCalculator {
        override fun weight(product: Product): Double =
            product.stock.unitsBySize.values.sum() * weight
    }
}