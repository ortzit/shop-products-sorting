package com.shop.products.domain.sortCriterion.stock

import com.shop.products.domain.sortCriterion.SortCriterion
import com.shop.products.domain.sortCriterion.SortCriterionCalculator
import com.shop.products.domain.sortCriterion.SortCriterionType
import com.shop.products.domain.sortCriterion.anySortCriterionWeight

fun anyStockCriterionWeightCalculator(
    weight: Double = anySortCriterionWeight()
): SortCriterionCalculator =
    StockCriterionCalculatorBuilder()
        .build(
            SortCriterion(
                type = SortCriterionType.STOCK,
                weight = weight,
            )
        )!!