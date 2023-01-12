package com.shop.products.infrastructure.repository

import com.shop.products.domain.Product
import com.shop.products.domain.ProductRepository
import com.shop.products.infrastructure.repository.ProductRepositoryConstants.PRODUCTS_DB
import com.shop.products.infrastructure.repository.ProductRepositoryConstants.PRODUCTS_ID_FIELD
import com.shop.products.infrastructure.repository.ProductRepositoryConstants.PRODUCTS_NAME_FIELD
import com.shop.products.infrastructure.repository.ProductRepositoryConstants.PRODUCTS_SALES_UNITS_FIELD
import com.shop.products.infrastructure.repository.ProductRepositoryConstants.PRODUCTS_TABLE
import com.shop.products.infrastructure.repository.ProductRepositoryConstants.STOCKS_PRODUCT_ID_FIELD
import com.shop.products.infrastructure.repository.ProductRepositoryConstants.STOCKS_SIZE_FIELD
import com.shop.products.infrastructure.repository.ProductRepositoryConstants.STOCKS_TABLE
import com.shop.products.infrastructure.repository.ProductRepositoryConstants.STOCKS_UNITS_FIELD
import org.springframework.core.io.DefaultResourceLoader
import org.springframework.stereotype.Repository
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet

@Repository
class SqliteProductRepository(
    private val dbName: String = PRODUCTS_DB,
) : ProductRepository {
    init {
        ProductRepositoryInitializer(getConnection()).run()
    }

    override fun getAll(): List<Product> =
        getConnection().let { connection ->
            connection.createStatement().let { statement ->
                val query =
                    """
                        SELECT
                            p.$PRODUCTS_ID_FIELD, p.$PRODUCTS_NAME_FIELD, p.$PRODUCTS_SALES_UNITS_FIELD,
                            s.$STOCKS_SIZE_FIELD, s.$STOCKS_UNITS_FIELD
                        FROM $PRODUCTS_TABLE p
                        INNER JOIN $STOCKS_TABLE s ON s.$STOCKS_PRODUCT_ID_FIELD = p.$PRODUCTS_ID_FIELD
                    """.trimIndent()
                val productsResultSet = statement.executeQuery(query)
                val result = productsResultSet.toDomain()
                statement.close()
                connection.close()
                result
            }
        }

    fun ResultSet.toDomain(): List<Product> {
        val products = arrayListOf<Product>()
        var lastProcessedProduct: ProductEntity? = null

        for (row in generateSequence { if (next()) this else null }) {
            val product = row.readProductEntityWithEmptyStock()
            val stock = row.readStockEntity()

            lastProcessedProduct?.let {
                if (product.id != it.id) {
                    products.add(it.toDomain())
                    lastProcessedProduct = product
                }
            } ?: let { lastProcessedProduct = product }

            lastProcessedProduct!!.stockList.add(stock)
        }

        lastProcessedProduct?.let { products.add(it.toDomain()) }

        return products
    }

    fun ResultSet.readProductEntityWithEmptyStock(): ProductEntity {
        val id = getInt(PRODUCTS_ID_FIELD)
        val name = getString(PRODUCTS_NAME_FIELD)
        val salesUnits = getInt(PRODUCTS_SALES_UNITS_FIELD)
        return ProductEntity(id, name, salesUnits, arrayListOf())
    }

    fun ResultSet.readStockEntity(): StockEntity {
        val productId = getInt(PRODUCTS_ID_FIELD)
        val size = getString(STOCKS_SIZE_FIELD)
        val units = getInt(STOCKS_UNITS_FIELD)
        return StockEntity(productId, size, units)
    }

    private fun getConnection(): Connection =
        DefaultResourceLoader().getResource("classpath:$dbName").file.path.let { path ->
            DriverManager.getConnection("jdbc:sqlite:$path")
        }
}