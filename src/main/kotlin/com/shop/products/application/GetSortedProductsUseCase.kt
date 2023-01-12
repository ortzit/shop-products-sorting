package com.shop.products.application

import com.shop.products.domain.Product
import com.shop.products.domain.ProductService
import com.shop.products.domain.sortCriterion.SortCriterionCalculatorFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class GetSortedProductsUseCase @Autowired constructor(
    private val getSortedProductsQueryValidator: GetSortedProductsQueryValidator,
    private val sortCriterionCalculatorFactory: SortCriterionCalculatorFactory,
    private val productService: ProductService,
) {
    fun query(query: GetSortedProductsQuery): GetSortedProductsResult {
        getSortedProductsQueryValidator.validate(query)

        return query.toDomain().let { sortCriteria ->
            sortCriterionCalculatorFactory.build(sortCriteria)
                .let { sortCriterionCalculators ->
                    productService.getAllProductsSorted(sortCriterionCalculators).toResult()
                }
        }
    }

    private fun List<Product>.toResult(): GetSortedProductsResult =
        GetSortedProductsResult.fromDomain(this)
}