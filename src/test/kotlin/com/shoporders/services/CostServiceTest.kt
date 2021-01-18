package com.shoporders.services

import com.shoporders.OrderException
import com.shoporders.domain.Item
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.micronaut.test.extensions.kotest.annotation.MicronautTest


@MicronautTest
class CostServiceTest(val costService: CostService) : StringSpec({

    "test Apple" {
        assert(costService.priceOf(Item("Apple", 1)) == 60L)
        assert(costService.priceOf(Item("Apple", 2)) == 60L)
        assert(costService.priceOf(Item("Apple", 3)) == 120L)
    }

    "test Orange" {
        assert(costService.priceOf(Item("Orange", 1)) == 25L)
        assert(costService.priceOf(Item("Orange", 2)) == 50L)
        assert(costService.priceOf(Item("Orange", 3)) == 50L)
        assert(costService.priceOf(Item("Orange", 4)) == 75L)
    }

    "test unknown" {
        shouldThrow<OrderException> {
            costService.priceOf(Item("Orangutan", 1))
        }
    }
})