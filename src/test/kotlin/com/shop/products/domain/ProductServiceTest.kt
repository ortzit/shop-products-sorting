package com.shop.products.domain

import com.shop.products.anyNotEmptyListOf
import com.shop.products.domain.sortCriterion.SortCriterionCalculator
import com.shop.products.domain.sortCriterion.anySortCriterionWeight
import com.shop.products.domain.sortCriterion.salesUnits.anySalesUnitsCriterionWeightCalculator
import com.shop.products.domain.sortCriterion.stock.anyStockCriterionWeightCalculator
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ProductServiceTest {
    private val productsRepository: ProductRepository = mock()
    private val service: ProductService = ProductService(productsRepository)

    @Test
    fun `should return products in the original order when no criterion applied`() {
        // given
        val sortCriteria = listOf<SortCriterionCalculator>()
        val productsToSort = anyNotEmptyListOf { anyProduct() }
        whenever(productsRepository.getAll())
            .thenReturn(productsToSort)

        // when
        val result = service.getAllProductsSorted(sortCriteria)

        // then
        assertEquals(productsToSort, result)
    }

    @Test
    fun `should sort by sales units descending when applying sales units criterion`() {
        // given
        val sortCriteria = listOf(
            anySalesUnitsCriterionWeightCalculator()
        )
        val productsToSort = anyNotEmptyListOf { anyProduct() }
        whenever(productsRepository.getAll())
            .thenReturn(productsToSort)

        // when
        val result = service.getAllProductsSorted(sortCriteria)

        // then
        val expected = productsToSort.sortedByDescending { it.salesUnits }
        assertEquals(expected, result)
    }

    @Test
    fun `should sort by stock units descending when applying stock units criterion`() {
        // given
        val sortCriteria = listOf(
            anyStockCriterionWeightCalculator()
        )
        val productsToSort = anyNotEmptyListOf { anyProduct() }
        whenever(productsRepository.getAll())
            .thenReturn(productsToSort)

        // when
        val result = service.getAllProductsSorted(sortCriteria)

        // then
        val expected = productsToSort.sortedByDescending { it.stock.unitsBySize.values.sum() }
        assertEquals(expected, result)
    }

    @Test
    fun `should sort using both sales and stock units descending when applying both sales and stock units criteria`() {
        // given
        val salesUnitsWeight = anySortCriterionWeight()
        val stockWeight = anySortCriterionWeight()
        val sortCriteria = listOf(
            anySalesUnitsCriterionWeightCalculator(salesUnitsWeight),
            anyStockCriterionWeightCalculator(stockWeight)
        )

        val productsToSort = anyNotEmptyListOf { anyProduct() }
        whenever(productsRepository.getAll())
            .thenReturn(productsToSort)

        // when
        val result = service.getAllProductsSorted(sortCriteria)

        // then
        val expected = productsToSort.sortedByDescending {
            it.salesUnits * salesUnitsWeight + it.stock.unitsBySize.values.sum() * stockWeight
        }
        assertEquals(expected, result)
    }
}