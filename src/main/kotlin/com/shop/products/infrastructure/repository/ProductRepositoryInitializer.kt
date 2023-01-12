package com.shop.products.infrastructure.repository

import com.shop.products.infrastructure.repository.ProductRepositoryConstants.PRODUCTS_FILE_PATH
import com.shop.products.infrastructure.repository.ProductRepositoryConstants.PRODUCTS_ID_FIELD
import com.shop.products.infrastructure.repository.ProductRepositoryConstants.PRODUCTS_NAME_FIELD
import com.shop.products.infrastructure.repository.ProductRepositoryConstants.PRODUCTS_SALES_UNITS_FIELD
import com.shop.products.infrastructure.repository.ProductRepositoryConstants.PRODUCTS_TABLE
import com.shop.products.infrastructure.repository.ProductRepositoryConstants.STOCKS_FILE_PATH
import com.shop.products.infrastructure.repository.ProductRepositoryConstants.STOCKS_PRODUCT_ID_FIELD
import com.shop.products.infrastructure.repository.ProductRepositoryConstants.STOCKS_SIZE_FIELD
import com.shop.products.infrastructure.repository.ProductRepositoryConstants.STOCKS_TABLE
import com.shop.products.infrastructure.repository.ProductRepositoryConstants.STOCKS_UNITS_FIELD
import java.sql.Connection

class ProductRepositoryInitializer(
    private val connection: Connection,
) {
    fun run() {
        createTables()
        resetData()
    }

    private fun createTables() {
        createProductsTable()
        createStocksTable()
    }

    private fun resetData() {
        clearStocksTable()
        clearProductsTable()
        loadProducts()
        loadStocks()
        connection.close()
    }

    private fun createProductsTable() {
        val createProductsTableQuery =
            """
                CREATE TABLE IF NOT EXISTS $PRODUCTS_TABLE (
                    $PRODUCTS_ID_FIELD INTEGER, 
                    $PRODUCTS_NAME_FIELD TEXT NOT NULL, 
                    $PRODUCTS_SALES_UNITS_FIELD INTEGER NOT NULL,
                    PRIMARY KEY ($PRODUCTS_ID_FIELD) 
                )
            """.trimIndent()
        connection.createStatement().let { statement ->
            statement.executeUpdate(createProductsTableQuery)
            statement.close()
        }
    }

    private fun createStocksTable() {
        val createStocksTableQuery =
            """
                CREATE TABLE IF NOT EXISTS $STOCKS_TABLE (
                    $STOCKS_PRODUCT_ID_FIELD INTEGER, 
                    $STOCKS_SIZE_FIELD TEXT, 
                    $STOCKS_UNITS_FIELD INTEGER NOT NULL DEFAULT 0, 
                    PRIMARY KEY ($STOCKS_PRODUCT_ID_FIELD, $STOCKS_SIZE_FIELD), 
                    FOREIGN KEY ($STOCKS_PRODUCT_ID_FIELD) REFERENCES $PRODUCTS_TABLE($PRODUCTS_ID_FIELD)
                )
            """.trimIndent()
        connection.createStatement().let { statement ->
            statement.executeUpdate(createStocksTableQuery)
            statement.close()
        }
    }

    private fun clearStocksTable() {
        clearTable(STOCKS_TABLE)
    }

    private fun clearProductsTable() {
        clearTable(PRODUCTS_TABLE)
    }

    private fun clearTable(tableName: String) {
        connection.createStatement().let { statement ->
            statement.executeUpdate("DELETE FROM $tableName")
            statement.close()
        }
    }

    private fun loadProducts() {
        loadData(PRODUCTS_FILE_PATH)
    }

    private fun loadStocks() {
        loadData(STOCKS_FILE_PATH)
    }

    private fun loadData(filePath: String) {
        javaClass.getResourceAsStream("/$filePath")?.let { stockInputStream ->
            val stocksInsertQueries = stockInputStream.bufferedReader().use { it.readText() }
            stockInputStream.close()
            connection.createStatement().let { statement ->
                statement.executeUpdate(stocksInsertQueries)
                statement.close()
            }
        }
    }
}