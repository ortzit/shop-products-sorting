package com.shop.products

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@OpenAPIDefinition(
    info = Info(title = "Products API", version = "1.0.0", description = "API to operate with products")
)
class Starter

fun main(args: Array<String>) {
    runApplication<com.shop.products.Starter>(*args)
}