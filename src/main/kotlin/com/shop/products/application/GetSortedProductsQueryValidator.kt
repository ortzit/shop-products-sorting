package com.shop.products.application

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class GetSortedProductsQueryValidator {
    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    fun validate(query: GetSortedProductsQuery) {
        this.takeIf { query.salesUnitsWeight < 0 || query.stockWeight < 0 }?.let {
            val ex = NegativeSortingWeightException("Sorting weight values must be greater than zero")
            logger.warn("${ex::class} - ${query.salesUnitsWeight} or ${query.stockWeight} are negative")
            throw ex
        }
    }
}