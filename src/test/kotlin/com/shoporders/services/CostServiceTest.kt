package com.shoporders.services

import com.shoporders.OrderException
import com.shoporders.domain.Item
import com.shoporders.domain.Order
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.micronaut.test.extensions.kotest.annotation.MicronautTest


@MicronautTest
class CostServiceTest(val costService: CostService) : StringSpec({

    "test Apple" {
        val apple = Item(name = "Apple", cost = 60L)
        assert(costService.priceOf(Order.LineItem(apple, 1)) == 60L)
        assert(costService.priceOf(Order.LineItem(apple, 2)) == 60L)
        assert(costService.priceOf(Order.LineItem(apple, 3)) == 120L)
    }

    "test Orange" {
        val orange = Item(name = "Orange", cost = 25L)
        assert(costService.priceOf(Order.LineItem(orange, 1L)) == 25L)
        assert(costService.priceOf(Order.LineItem(orange, 2)) == 50L)
        assert(costService.priceOf(Order.LineItem(orange, 3)) == 50L)
        assert(costService.priceOf(Order.LineItem(orange, 4)) == 75L)
    }

    "test unknown" {
        val orangutan = Item(name = "Orangutan", cost = 33L)
        shouldThrow<OrderException> {
            costService.priceOf(Order.LineItem(orangutan, 1))
        }
    }
})