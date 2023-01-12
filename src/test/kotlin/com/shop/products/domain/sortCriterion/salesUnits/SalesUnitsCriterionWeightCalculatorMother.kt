package com.shop.products.domain.sortCriterion.salesUnits

import com.shop.products.domain.sortCriterion.SortCriterion
import com.shop.products.domain.sortCriterion.SortCriterionCalculator
import com.shop.products.domain.sortCriterion.SortCriterionType
import com.shop.products.domain.sortCriterion.anySortCriterionWeight

fun anySalesUnitsCriterionWeightCalculator(
    weight: Double = anySortCriterionWeight()
): SortCriterionCalculator =
    SalesUnitsSortCriterionCalculatorBuilder()
        .build(
            SortCriterion(
                type = SortCriterionType.SALES_UNITS,
                weight = weight,
            )
        )!!