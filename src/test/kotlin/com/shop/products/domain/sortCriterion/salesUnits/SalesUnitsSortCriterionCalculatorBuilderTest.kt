package com.shop.products.domain.sortCriterion.salesUnits

import com.shop.products.domain.anyProduct
import com.shop.products.domain.sortCriterion.anySalesUnitsSortCriterion
import com.shop.products.domain.sortCriterion.anyStockSortCriterion
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class SalesUnitsSortCriterionCalculatorBuilderTest {
    private val builder: SalesUnitsSortCriterionCalculatorBuilder = SalesUnitsSortCriterionCalculatorBuilder()

    @Test
    fun `should return the sort criterion when building by sales units sort criterion type`() {
        // given
        val sortCriterion = anySalesUnitsSortCriterion()

        // when
        val result = builder.build(sortCriterion)

        // then
        assertNotNull(result)
    }

    @Test
    fun `should return null when building by stock sort criterion type`() {
        // given
        val sortCriterion = anyStockSortCriterion()

        // when
        val result = builder.build(sortCriterion)

        // then
        assertNull(result)
    }

    @Test
    fun `should return sales units multiplied by sort criterion weight`() {
        // given
        val sortCriterion = anySalesUnitsSortCriterion()
        val sortCriterionCalculator = builder.build(sortCriterion)!!
        val product = anyProduct()

        // when
        val result = sortCriterionCalculator.weight(product)

        // then
        val expected = product.salesUnits * sortCriterion.weight
        assertEquals(expected, result)
    }
}