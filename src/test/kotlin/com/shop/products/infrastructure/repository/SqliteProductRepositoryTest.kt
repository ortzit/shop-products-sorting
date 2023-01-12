package com.shop.products.infrastructure.repository

import com.shop.products.domain.Product
import com.shop.products.domain.ProductId
import com.shop.products.domain.Size
import com.shop.products.domain.Stock
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class SqliteProductRepositoryTest {
    @Test
    fun `should return six products when getting all`() {
        // given
        val sqliteProductRepository = SqliteProductRepository(TEST_DB)

        // when
        val result = sqliteProductRepository.getAll()

        // then
        assertEquals(6, result.size)
    }

    @Test
    fun `should return product 1 with the expected values when getting all`() {
        // given
        val sqliteProductRepository = SqliteProductRepository(TEST_DB)

        // when
        val result = sqliteProductRepository.getAll()
            .filter { it.id == ProductId(1) }

        // then
        val expected = listOf(
            Product(
                id = ProductId(1),
                name = "V-NECH BASIC SHIRT",
                salesUnits = 100,
                stock = Stock(
                    mapOf(Size.S to 4, Size.M to 9, Size.L to 0)
                )
            )
        )
        assertEquals(expected, result)
    }

    @Test
    fun `should return product 2 with the expected values when getting all`() {
        // given
        val sqliteProductRepository = SqliteProductRepository(TEST_DB)

        // when
        val result = sqliteProductRepository.getAll()
            .filter { it.id == ProductId(2) }

        // then
        val expected = listOf(
            Product(
                id = ProductId(2),
                name = "CONTRASTING FABRIC T-SHIRT",
                salesUnits = 50,
                stock = Stock(
                    mapOf(Size.S to 35, Size.M to 9, Size.L to 9)
                )
            )
        )
        assertEquals(expected, result)
    }

    @Test
    fun `should return product 3 with the expected values when getting all`() {
        // given
        val sqliteProductRepository = SqliteProductRepository(TEST_DB)

        // when
        val result = sqliteProductRepository.getAll()
            .filter { it.id == ProductId(3) }

        // then
        val expected = listOf(
            Product(
                id = ProductId(3),
                name = "RAISED PRINT T-SHIRT",
                salesUnits = 80,
                stock = Stock(
                    mapOf(Size.S to 20, Size.M to 2, Size.L to 20)
                )
            )
        )
        assertEquals(expected, result)
    }

    @Test
    fun `should return product 4 with the expected values when getting all`() {
        // given
        val sqliteProductRepository = SqliteProductRepository(TEST_DB)

        // when
        val result = sqliteProductRepository.getAll()
            .filter { it.id == ProductId(4) }

        // then
        val expected = listOf(
            Product(
                id = ProductId(4),
                name = "PLEATED T-SHIRT",
                salesUnits = 3,
                stock = Stock(
                    mapOf(Size.S to 25, Size.M to 30, Size.L to 10)
                )
            )
        )
        assertEquals(expected, result)
    }

    @Test
    fun `should return product 5 with the expected values when getting all`() {
        // given
        val sqliteProductRepository = SqliteProductRepository(TEST_DB)

        // when
        val result = sqliteProductRepository.getAll()
            .filter { it.id == ProductId(5) }

        // then
        val expected = listOf(
            Product(
                id = ProductId(5),
                name = "CONTRASTING LACE T-SHIRT",
                salesUnits = 650,
                stock = Stock(
                    mapOf(Size.S to 0, Size.M to 1, Size.L to 0)
                )
            )
        )
        assertEquals(expected, result)
    }

    @Test
    fun `should return product 6 with the expected values when getting all`() {
        // given
        val sqliteProductRepository = SqliteProductRepository(TEST_DB)

        // when
        val result = sqliteProductRepository.getAll()
            .filter { it.id == ProductId(6) }

        // then
        val expected = listOf(
            Product(
                id = ProductId(6),
                name = "SLOGAN T-SHIRT",
                salesUnits = 20,
                stock = Stock(
                    mapOf(Size.S to 9, Size.M to 2, Size.L to 5)
                )
            )
        )
        assertEquals(expected, result)
    }

    companion object {
        private const val TEST_DB = "products_test.sqlite"
    }
}