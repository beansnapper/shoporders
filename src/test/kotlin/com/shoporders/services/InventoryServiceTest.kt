package com.shoporders.services

import com.shoporders.OrderException
import com.shoporders.domain.Item
import com.shoporders.domain.Order
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.micronaut.test.extensions.kotest.annotation.MicronautTest

@MicronautTest
class InventoryServiceTest(val inventoryService: InventoryService) : StringSpec({


    "successful Oranges provisioning" {
        val orange = Item(name = "Orange", cost = 25L)
        val order1 = Order(listOf(Order.LineItem(orange, 3)))
        inventoryService.provision(order1)  // uneventful success
    }

    "item not available in sufficient quantities" {
        val apple = Item(name = "Apple", cost = 60L)
        val order1 = Order(listOf(Order.LineItem(apple, 8)))
        val order2 = Order(listOf(Order.LineItem(apple, 4)))
        inventoryService.provision(order1)
        shouldThrow<OrderException> {
            inventoryService.provision(order2)
        }
    }

    "item not found" {
        val quafluffuls = Item(name = "quafluffuls", cost = 99L)
        shouldThrow<OrderException> {
            inventoryService.provision(Order(listOf(Order.LineItem(quafluffuls, 4))))
        }
    }


})