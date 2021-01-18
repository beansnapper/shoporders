package com.shoporders.services

import com.shoporders.domain.Order
import com.shoporders.domain.Status
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OrderService {

    @Inject
    lateinit var costService: CostService

    @Inject
    lateinit var notificationService: NotificationService

    fun submit(order: Order): Order {
        val completedOrder = order.copy(status = Status.SUCCESS, subtotal = costService.priceOf(order))
        notificationService.notify(completedOrder)
        return completedOrder
    }

}