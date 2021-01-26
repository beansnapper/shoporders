package com.shoporders.services

import com.shoporders.domain.Item
import com.shoporders.domain.Order
import io.kotest.core.spec.style.StringSpec
import io.micronaut.test.extensions.kotest.annotation.MicronautTest

@MicronautTest
class OrderServiceTest(val orderService: OrderService) : StringSpec({

    "test List" {
        val apple = Item(name = "Apple", cost = 60L)
        val orange = Item(name = "Orange", cost = 25L)

        val order = Order(
            listOf(Order.LineItem(apple, 3), Order.LineItem(orange, 1))
        )

        val total = orderService.submit(order)
        print("total = $total")
        val completedOrder = orderService.submit(order)
        assert(completedOrder.subtotal == 145L)
        assert(completedOrder.status == Order.Status.SUCCESS)
    }

})