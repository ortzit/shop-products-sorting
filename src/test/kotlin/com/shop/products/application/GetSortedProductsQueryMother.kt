package com.shop.products.application

import com.shop.products.domain.sortCriterion.anySortCriterionWeight

fun anyGetSortedProductsQuery(): GetSortedProductsQuery =
    GetSortedProductsQuery(
        salesUnitsWeight = anySortCriterionWeight(),
        stockWeight = anySortCriterionWeight(),
    )