package com.shop.products.domain.sortCriterion

import com.shop.products.domain.Product

interface SortCriterionCalculator {
    fun weight(product: Product): Double
}