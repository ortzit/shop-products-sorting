package com.shop.products.domain.sortCriterion

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SortCriterionCalculatorFactory @Autowired constructor(
    private val sortCriterionCalculatorBuilders: List<SortCriterionCalculatorBuilder>,
) {
    fun build(sortCriterion: Collection<SortCriterion>): Collection<SortCriterionCalculator> =
        sortCriterion.mapNotNull {
            build(it)
        }

    private fun build(sortCriterion: SortCriterion): SortCriterionCalculator? =
        sortCriterionCalculatorBuilders.firstNotNullOfOrNull {
            it.build(sortCriterion)
        }
}