package com.shop.products.application

import com.shop.products.anyNegativeValueDouble
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class GetSortedProductsQueryValidatorTest {
    private val validator: GetSortedProductsQueryValidator =
        GetSortedProductsQueryValidator()

    @Test
    fun `should throw no errors when validating valid query`() {
        // given
        val query = anyGetSortedProductsQuery()

        // expect
        validator.validate(query)
    }

    @Test
    fun `should error when validating valid query with negative sales units weight`() {
        // given
        val query = anyGetSortedProductsQuery().copy(salesUnitsWeight = anyNegativeValueDouble())

        // expect
        assertThrows<NegativeSortingWeightException> {
            validator.validate(query)
        }
    }

    @Test
    fun `should error when validating valid query with negative stock weight`() {
        // given
        val query = anyGetSortedProductsQuery().copy(stockWeight = anyNegativeValueDouble())

        // expect
        assertThrows<NegativeSortingWeightException> {
            validator.validate(query)
        }
    }
}