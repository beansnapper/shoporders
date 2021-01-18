package com.shoporders.services

import com.shoporders.OrderException
import com.shoporders.domain.Item
import com.shoporders.domain.Order
import javax.inject.Singleton

@Singleton
class CostService {
    companion object {
        val standard = { unitPrice: Long, qty: Long -> unitPrice * qty }
    }

    interface Offer {
        fun apply(unitPrice: Long, qty: Long): Long
    }

    class Promotion(val buyCount: Long, val priceCount: Long) : Offer {

        override fun apply(unitPrice: Long, qty: Long): Long {
            return ((qty / buyCount) * priceCount * unitPrice) +
                    standard.invoke(unitPrice, qty % buyCount)
        }

    }

    private val map = mapOf("Apple" to 60L, "Orange" to 25L)
    private val promotions =
        mapOf("Apple" to Promotion(2, 1), "Orange" to Promotion(3, 2))

    fun priceOf(order: Order): Long {
        return order.items.map { priceOf(it) }.sum()
    }

    fun priceOf(item: Item): Long {
        val unitCost = map[item.name] ?: throw OrderException("Unknown Item")
        return when (val promotion = promotions[item.name]) {
            null -> standard.invoke(unitCost, item.qty)
            else -> promotion.apply(unitCost, item.qty)
        }
    }


}