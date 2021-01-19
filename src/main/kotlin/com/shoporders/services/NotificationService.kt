package com.shoporders.services

import com.shoporders.domain.Order
import javax.inject.Singleton

/**
 * let the customer know the status of their order
 */
@Singleton
class NotificationService {

    fun notify(order: Order) {
        print("Order Complete: $order")
    }
}