package com.shoporders.services

import com.shoporders.domain.Order
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OrderService {

    @Inject
    lateinit var costService: CostService

    fun calcTotal(order: Order): Long {
        return order.items.map {
            costService.priceOf(order)
        }.sum()
    }

}