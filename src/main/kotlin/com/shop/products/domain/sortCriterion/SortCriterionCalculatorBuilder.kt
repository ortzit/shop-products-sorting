package com.shop.products.domain.sortCriterion

interface SortCriterionCalculatorBuilder {
    fun build(sortCriterion: SortCriterion): SortCriterionCalculator?
}