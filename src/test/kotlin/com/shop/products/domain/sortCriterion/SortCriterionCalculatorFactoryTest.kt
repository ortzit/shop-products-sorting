package com.shop.products.domain.sortCriterion

import com.shop.products.anyNotEmptyListOf
import com.shop.products.domain.Product
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class SortCriterionCalculatorFactoryTest {

    @Test
    fun `should return no sort criterion calculator when there are no sort criterion calculator builders`() {
        // given
        val factory = SortCriterionCalculatorFactory(emptyList())
        val sortCriteria = anyNotEmptyListOf { anySortCriterion() }

        // when
        val result = factory.build(sortCriteria)

        // then
        assertTrue(result.isEmpty())
    }

    @Test
    fun `should return no sort criterion calculator when not matching with any sort criterion calculator builder`() {
        // given
        val builders = listOf(
            sortCriterionCalculatorBuilderStub(SortCriterionType.SALES_UNITS)
        )
        val factory = SortCriterionCalculatorFactory(builders)

        // when
        val sortCriteria = listOf(anyStockSortCriterion())
        val result = factory.build(sortCriteria)

        // then
        assertTrue(result.isEmpty())
    }

    @Test
    fun `should return a sort criterion calculator for every different sort criterion`() {
        // given
        val sortCriteria = anyNotEmptyListOf { anySortCriterion() }

        val differentTargetTypes = sortCriteria.map { it.type }.toSet()
        val builders: List<SortCriterionCalculatorBuilder> = differentTargetTypes.map { sortCriterionType ->
            sortCriterionCalculatorBuilderStub(sortCriterionType)
        }
        val factory = SortCriterionCalculatorFactory(builders)

        // when
        val result = factory.build(sortCriteria)

        // then
        assertEquals(sortCriteria.size, result.size)
    }

    private fun sortCriterionCalculatorBuilderStub(targetType: SortCriterionType): SortCriterionCalculatorBuilder =
        object : SortCriterionCalculatorBuilder {
            override fun build(sortCriterion: SortCriterion): SortCriterionCalculator? =
                sortCriterion.takeIf { it.type == targetType }?.let {
                    sortCriterionCalculatorStub()
                }
        }

    private fun sortCriterionCalculatorStub(): SortCriterionCalculator =
        object : SortCriterionCalculator {
            override fun weight(product: Product): Double = 0.0
        }
}