package com.shop.products.domain

import com.shop.products.domain.sortCriterion.SortCriterionCalculator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ProductService @Autowired constructor(
    private val productsRepository: ProductRepository,
) {
    fun getAllProductsSorted(sortCriterionCalculator: Collection<SortCriterionCalculator>): List<Product> =
        productsRepository.getAll().let { products ->
            products.sortedByDescending { product ->
                sortCriterionCalculator.sumOf { it.weight(product) }
            }
        }
}