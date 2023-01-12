package com.shop.products

import java.util.*
import kotlin.math.absoluteValue
import kotlin.random.Random

fun anyPositiveInt(until: Int = Integer.MAX_VALUE): Int =
    Random.nextInt(
        from = 1,
        until = until
    )

fun anyAbsoluteValueInt(): Int =
    Random.nextInt().absoluteValue

fun anyAbsoluteValueDouble(): Double =
    Random.nextDouble().absoluteValue

fun anyNegativeValueDouble(): Double =
    -anyAbsoluteValueDouble()

fun anyString(): String = UUID.randomUUID().toString()

fun <T> anyNotEmptyListOf(function: () -> T): List<T> =
    (1..Random.nextInt(1, RANDOM_LIST_MAX_SIZE)).map { function.invoke() }

private const val RANDOM_LIST_MAX_SIZE = 5