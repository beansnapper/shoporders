package com.shoporders.services

import com.shoporders.OrderException
import com.shoporders.domain.Order
import java.util.concurrent.locks.ReentrantLock
import javax.inject.Singleton

/**
 * this should be backed by a datastore, but we'll just stub it out.
 */
@Singleton
class InventoryService {

    private val map = mutableMapOf("Apple" to 10L, "Orange" to 8L)
    private val mutex = ReentrantLock()

    /**
     * this is an all or nothing attempt - typically this would be
     * done in the database with row locks and deadlock detection.
     */
    fun provision(order: Order) {
        mutex.lock()
        try {
            // verify quantities
            order.items.forEach { item ->
                val qty = map[item.name] ?: throw OrderException("No ${item.name} available")
                if (item.qty > qty) {
                    throw OrderException("Only $qty ${item.name}(s) available")
                }
            }

            // commit
            order.items.forEach { item ->
                map[item.name] = map[item.name]!! - item.qty
            }


        } finally {
            mutex.unlock()
        }
    }

}