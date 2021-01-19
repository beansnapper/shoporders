package com.shoporders.services

import com.shoporders.domain.Item
import com.shoporders.domain.Order
import com.shoporders.domain.Status
import io.kotest.core.spec.style.StringSpec
import io.micronaut.test.extensions.kotest.annotation.MicronautTest

@MicronautTest
class OrderServiceTest(val orderService: OrderService) : StringSpec({

    "test List" {
        val order = Order(
            listOf(Item("Apple", 3), Item("Orange", 1))
        )


        val total = orderService.submit(order)
        print("total = $total")
        val completedOrder = orderService.submit(order)
        assert(completedOrder.subtotal == 145L)
        assert(completedOrder.status == Status.SUCCESS)
    }


})