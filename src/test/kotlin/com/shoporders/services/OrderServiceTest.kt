package com.shoporders.services

import com.shoporders.domain.Item
import com.shoporders.domain.Order
import io.kotest.core.spec.style.StringSpec
import io.micronaut.test.extensions.kotest.annotation.MicronautTest

@MicronautTest
class OrderServiceTest(val orderService: OrderService) : StringSpec({

    "test List" {
        val order = Order(
            listOf(Item("Apple"), Item("Apple"), Item("Orange"), Item("Apple"))
        )

        assert(orderService.calcTotal(order) == 205L)
    }


})