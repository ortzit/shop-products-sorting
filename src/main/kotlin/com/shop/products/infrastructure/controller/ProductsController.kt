package com.shop.products.infrastructure.controller

import com.shop.products.application.GetSortedProductsQuery
import com.shop.products.application.GetSortedProductsUseCase
import com.shop.products.application.NegativeSortingWeightException
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springdoc.api.annotations.ParameterObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RestController

@RestController
class ProductsController @Autowired constructor(
    private val getSortedProductsUseCase: GetSortedProductsUseCase,
    private val responseEntityMapper: ResponseEntityMapper,
) {
    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    @GetMapping(GET_PRODUCTS_PATH)
    @Operation(summary = "Gets a list of all products sorted by specified criteria")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = HTTP_200,
                content = [Content(
                    mediaType = "application/json",
                    array = ArraySchema(schema = Schema(implementation = ProductResponse::class))
                )]
            ),
            ApiResponse(
                responseCode = HTTP_400,
                description = "One of the request parameters is negative",
                content = [Content(mediaType = "*/*", schema = Schema(example = "string"))]
            ),
            ApiResponse(
                responseCode = HTTP_500,
                description = "Not controlled error thrown",
                content = [Content(mediaType = "*/*", schema = Schema(example = "string"))]
            ),
            ApiResponse(
                responseCode = HTTP_501,
                description = "Unknown response received",
                content = [Content(mediaType = "*/*", schema = Schema(example = "string"))]
            )
        ]
    )
    fun getProducts(@ParameterObject @ModelAttribute request: ProductsRequest): ResponseEntity<*> {
        logger.debug("Request received in [$GET_PRODUCTS_PATH] - request: [$request]")

        return GetSortedProductsQuery(
            salesUnitsWeight = request.sortingSalesUnitsWeight,
            stockWeight = request.sortingStockWeight,
        ).let { query ->
            try {
                getSortedProductsUseCase.query(query)
            } catch (ex: NegativeSortingWeightException) {
                logger.warn("[$GET_PRODUCTS_PATH]: ${ex::class} error received with request: [$request]")
                ex
            } catch (th: Throwable) {
                logger.error("[$GET_PRODUCTS_PATH]: Uncontrolled ${th::class} error received with request: [$request]")
                th
            }.let { result ->
                responseEntityMapper.toResponseEntity(result)
            }
        }
    }

    companion object {
        private const val GET_PRODUCTS_PATH: String = "/products"
        private const val HTTP_200 = "200"
        private const val HTTP_400 = "400"
        private const val HTTP_500 = "500"
        private const val HTTP_501 = "501"
    }
}