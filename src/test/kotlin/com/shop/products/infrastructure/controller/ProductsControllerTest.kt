package com.shop.products.infrastructure.controller

import com.shop.products.anyNegativeValueDouble
import com.shop.products.application.GetSortedProductsUseCase
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus

@SpringBootTest
class ProductsControllerTest {
    @Autowired
    private lateinit var getSortedProductsUseCase: GetSortedProductsUseCase

    @Autowired
    private lateinit var responseEntityMapper: ResponseEntityMapper

    @Test
    fun `should return bad request status code when sending a negative sorting weight`() {
        // given
        val controller = productsController()
        val request = anyProductsRequest()
            .copy(sortingSalesUnitsWeight = anyNegativeValueDouble())

        // when
        val result = controller.getProducts(request = request)

        // then
        assertEquals(HttpStatus.BAD_REQUEST, result.statusCode)
    }

    @Test
    fun `should return internal server error status when sending any non-controlled error`() {
        // given
        val getSortedProductsUseCase: GetSortedProductsUseCase = mock()
        val controller = productsController(useCase = getSortedProductsUseCase)

        whenever(
            getSortedProductsUseCase.query(any())
        ).thenThrow(NotImplementedError())

        // when
        val result = controller.getProducts(anyProductsRequest())

        // then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.statusCode)
    }

    @Test
    fun `should return unsorted products and ok status code when no sorting params sent`() {
        // given
        val controller = productsController()
        val request = ProductsRequest()

        // when
        val result = controller.getProducts(request)

        // then
        assertEquals(HttpStatus.OK, result.statusCode)

        val expectedProducts = allProductsResponse()
        assertEquals(expectedProducts, result.body)
    }

    @Test
    fun `should return sorted products and ok status code when sales units and stock sorting params sent`() {
        // given
        val controller = productsController()
        val request = anyProductsRequest()

        // when
        val result = controller.getProducts(request)

        // then
        assertEquals(HttpStatus.OK, result.statusCode)

        val expectedProducts = allProductsResponse()
            .sortedByDescending {
                it.salesUnits * request.sortingSalesUnitsWeight +
                        it.stock.values.sum() * request.sortingStockWeight
            }
        assertEquals(expectedProducts, result.body)
    }

    private fun allProductsResponse(): List<ProductResponse> =
        listOf(
            ProductResponse(
                id = 1, name = PRODUCT_1_NAME, salesUnits = 100,
                stock = mapOf(STOCK_SIZE_S to 4, STOCK_SIZE_M to 9, STOCK_SIZE_L to 0)
            ),
            ProductResponse(
                id = 2, name = PRODUCT_2_NAME, salesUnits = 50,
                stock = mapOf(STOCK_SIZE_S to 35, STOCK_SIZE_M to 9, STOCK_SIZE_L to 9)
            ),
            ProductResponse(
                id = 3, name = PRODUCT_3_NAME, salesUnits = 80,
                stock = mapOf(STOCK_SIZE_S to 20, STOCK_SIZE_M to 2, STOCK_SIZE_L to 20)
            ),
            ProductResponse(
                id = 4, name = PRODUCT_4_NAME, salesUnits = 3,
                stock = mapOf(STOCK_SIZE_S to 25, STOCK_SIZE_M to 30, STOCK_SIZE_L to 10)
            ),
            ProductResponse(
                id = 5, name = PRODUCT_5_NAME, salesUnits = 650,
                stock = mapOf(STOCK_SIZE_S to 0, STOCK_SIZE_M to 1, STOCK_SIZE_L to 0)
            ),
            ProductResponse(
                id = 6, name = PRODUCT_6_NAME, salesUnits = 20,
                stock = mapOf(STOCK_SIZE_S to 9, STOCK_SIZE_M to 2, STOCK_SIZE_L to 5)
            )
        )

    private fun productsController(useCase: GetSortedProductsUseCase = getSortedProductsUseCase): ProductsController =
        ProductsController(
            getSortedProductsUseCase = useCase,
            responseEntityMapper = responseEntityMapper,
        )

    companion object {
        private const val PRODUCT_1_NAME = "V-NECH BASIC SHIRT"
        private const val PRODUCT_2_NAME = "CONTRASTING FABRIC T-SHIRT"
        private const val PRODUCT_3_NAME = "RAISED PRINT T-SHIRT"
        private const val PRODUCT_4_NAME = "PLEATED T-SHIRT"
        private const val PRODUCT_5_NAME = "CONTRASTING LACE T-SHIRT"
        private const val PRODUCT_6_NAME = "SLOGAN T-SHIRT"

        private const val STOCK_SIZE_S = "S"
        private const val STOCK_SIZE_M = "M"
        private const val STOCK_SIZE_L = "L"
    }
}