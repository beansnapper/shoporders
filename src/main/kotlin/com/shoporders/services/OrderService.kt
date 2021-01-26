package com.shoporders.services

import com.shoporders.OrderException
import com.shoporders.domain.Order
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OrderService {

    @Inject
    lateinit var costService: CostService

    @Inject
    lateinit var notificationService: NotificationService

    @Inject
    lateinit var inventoryService: InventoryService

    fun submit(order: Order): Order {
        try {
            inventoryService.provision(order)
            val completedOrder = order.copy(status = Order.Status.SUCCESS, subtotal = costService.priceOf(order))
            notificationService.notify(completedOrder)
            return completedOrder
        } catch (e: OrderException) {
            val failedOrder = order.copy(
                status = Order.Status.FAILURE,
                reason = e.message ?: "Unknown reason"
            )
            notificationService.notify(failedOrder)
            return failedOrder
        }
    }

}