package com.shoporders.services

import com.shoporders.OrderException
import javax.inject.Singleton

@Singleton
class CostService {

    private val map = mapOf("Apple" to 60L, "Orange" to 25L)

    fun priceFor(item: String) : Long {
        return map[item] ?: throw OrderException("Unknown Item")
    }

}