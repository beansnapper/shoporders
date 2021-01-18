package com.shoporders.services

import com.shoporders.OrderException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.micronaut.test.extensions.kotest.annotation.MicronautTest


@MicronautTest
class CostServiceTest(val costService: CostService) : StringSpec({

    "test Apple" {
        assert(costService.priceFor("Apple") == 60L)
    }

    "test Orange" {
        assert(costService.priceFor("Orange") == 25L)
    }

    "test unknown" {
        shouldThrow<OrderException> {
            costService.priceFor("Orangutan")
        }
    }
})