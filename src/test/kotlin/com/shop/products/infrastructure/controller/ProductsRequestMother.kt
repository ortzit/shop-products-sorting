package com.shop.products.infrastructure.controller

import com.shop.products.domain.sortCriterion.anySortCriterionWeight

fun anyProductsRequest(): ProductsRequest =
    ProductsRequest(
        sortingSalesUnitsWeight = anySortCriterionWeight(),
        sortingStockWeight = anySortCriterionWeight(),
    )