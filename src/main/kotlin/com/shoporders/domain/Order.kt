package com.shoporders.domain

import java.util.*

typealias Timestamp = Date

data class Order(
    val items: List<LineItem>,
    val subtotal: Long = 0,
    val status: Status = Status.NOT_SUBMITTED,
    val reason: String = "",
) {
    class LineItem(
        val item: Item,
        val qty: Long,
    )

    enum class Status {
        SUCCESS,
        FAILURE,
        NOT_SUBMITTED
    }
}
