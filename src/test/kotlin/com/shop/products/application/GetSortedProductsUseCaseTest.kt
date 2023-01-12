package com.shop.products.application

import com.shop.products.anyString
import com.shop.products.domain.ProductService
import com.shop.products.domain.sortCriterion.SortCriterion
import com.shop.products.domain.sortCriterion.SortCriterionCalculator
import com.shop.products.domain.sortCriterion.SortCriterionCalculatorFactory
import com.shop.products.domain.sortCriterion.SortCriterionType
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyZeroInteractions
import com.nhaarman.mockitokotlin2.whenever
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class GetSortedProductsUseCaseTest {
    private val getSortedProductsQueryValidator: GetSortedProductsQueryValidator = mock()
    private val sortCriterionCalculatorFactory: SortCriterionCalculatorFactory = mock()
    private val productService: ProductService = mock()

    private val getSortedProductsUseCase = GetSortedProductsUseCase(
        getSortedProductsQueryValidator = getSortedProductsQueryValidator,
        sortCriterionCalculatorFactory = sortCriterionCalculatorFactory,
        productService = productService,
    )

    @Test
    fun `should not call sortCriterionCalculatorFactory nor productService when validation failed`() {
        // given
        val query = anyGetSortedProductsQuery()

        whenever(getSortedProductsQueryValidator.validate(query))
            .thenThrow(NegativeSortingWeightException(anyString()))

        // expect
        assertThrows<NegativeSortingWeightException> {
            getSortedProductsUseCase.query(query)

            verifyZeroInteractions(sortCriterionCalculatorFactory)
            verifyZeroInteractions(productService)
        }
    }

    @Test
    fun `should call sortCriterionCalculatorFactory and productService when validation is successful`() {
        // given
        val query = anyGetSortedProductsQuery()
        val sortCriterionCalculators = emptyList<SortCriterionCalculator>()
        whenever(sortCriterionCalculatorFactory.build(query.asDomain()))
            .thenReturn(sortCriterionCalculators)

        // when
        getSortedProductsUseCase.query(query)

        // then
        verify(getSortedProductsQueryValidator).validate(query)
        verify(productService).getAllProductsSorted(sortCriterionCalculators)
    }

    private fun GetSortedProductsQuery.asDomain(): List<SortCriterion> =
        listOf(
            SortCriterion(
                type = SortCriterionType.SALES_UNITS,
                weight = this.salesUnitsWeight,
            ),
            SortCriterion(
                type = SortCriterionType.STOCK,
                weight = this.stockWeight,
            )
        )
}