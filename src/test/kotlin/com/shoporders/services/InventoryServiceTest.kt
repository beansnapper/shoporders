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
        val order1 = Order(listOf(Item("Orange", 3)))
        inventoryService.provision(order1)  // uneventful success
    }

    "item not available in sufficient quantities" {
        val order1 = Order(listOf(Item("Apple", 8)))
        val order2 = Order(listOf(Item("Apple", 4)))
        inventoryService.provision(order1)
        shouldThrow<OrderException> {
            inventoryService.provision(order2)
        }
    }

    "item not found" {
        shouldThrow<OrderException> {
            inventoryService.provision(Order(listOf(Item("quafluffuls", 4))))
        }
    }


})