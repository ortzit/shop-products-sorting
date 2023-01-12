package com.shop.products.domain.sortCriterion

import kotlin.random.Random

fun anySortCriterion(): SortCriterion =
    SortCriterion(
        type = anySortCriterionType(),
        weight = anySortCriterionWeight(),
    )

fun anySalesUnitsSortCriterion(): SortCriterion =
    anySortCriterion().copy(type = SortCriterionType.SALES_UNITS)

fun anyStockSortCriterion(): SortCriterion =
    anySortCriterion().copy(type = SortCriterionType.STOCK)

private fun anySortCriterionType(): SortCriterionType =
    Random.nextInt(
        from = 0,
        until = SortCriterionType.values().size
    ).let { idx ->
        SortCriterionType.values()[idx]
    }