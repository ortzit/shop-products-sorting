package com.shop.products.infrastructure.repository

object ProductRepositoryConstants {
    const val PRODUCTS_FILE_PATH = "db-data/products.sql"
    const val STOCKS_FILE_PATH = "db-data/stocks.sql"

    const val PRODUCTS_DB = "products.sqlite"

    const val PRODUCTS_TABLE = "products"
    const val STOCKS_TABLE = "stocks"

    const val PRODUCTS_ID_FIELD = "id"
    const val PRODUCTS_NAME_FIELD = "name"
    const val PRODUCTS_SALES_UNITS_FIELD = "sales_units"

    const val STOCKS_PRODUCT_ID_FIELD = "product_id"
    const val STOCKS_SIZE_FIELD = "size"
    const val STOCKS_UNITS_FIELD = "units"

}