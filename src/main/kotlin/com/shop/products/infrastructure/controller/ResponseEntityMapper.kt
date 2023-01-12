package com.shop.products.infrastructure.controller

import com.shop.products.application.GetSortedProductsResult
import com.shop.products.application.NegativeSortingWeightException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class ResponseEntityMapper {
    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    fun toResponseEntity(result: Any): ResponseEntity<*> =
        when (result) {
            is GetSortedProductsResult -> result.toResponseEntity()
            is NegativeSortingWeightException -> result.asBadRequestResponseEntity()
            is Throwable -> result.asInternalServerErrorResponseEntity()
            else -> result.asNotImplementedResponseEntity()
        }

    private fun GetSortedProductsResult.toResponseEntity() =
        ResponseEntity
            .ok()
            .body(
                this.products.map { productResult ->
                    ProductResponse(
                        id = productResult.id,
                        name = productResult.name,
                        salesUnits = productResult.salesUnits,
                        stock = productResult.stock
                    )
                }
            )

    private fun NegativeSortingWeightException.asBadRequestResponseEntity(): ResponseEntity<String> =
        ResponseEntity
            .badRequest()
            .body(this.message)

    private fun Throwable.asInternalServerErrorResponseEntity(): ResponseEntity<String> =
        ResponseEntity
            .internalServerError()
            .body(this.stackTrace.toString())

    private fun Any.asNotImplementedResponseEntity(): ResponseEntity<String> =
        ResponseEntity
            .status(HttpStatus.NOT_IMPLEMENTED)
            .body(this.toString())
            .also {
                logger.error("Not implemented error - response: [$this]")
            }
}
