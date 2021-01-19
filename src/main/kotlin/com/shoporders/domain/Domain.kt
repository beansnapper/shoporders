package com.shoporders.domain

data class Item(
    val name: String,
    val qty: Long,
)

enum class Status {
    SUCCESS,
    FAILURE,
    NOT_SUBMITTED
}

data class Order(
    val items: List<Item>,
    val subtotal: Long = 0,
    val status: Status = Status.NOT_SUBMITTED,
    val reason: String = "",
)
