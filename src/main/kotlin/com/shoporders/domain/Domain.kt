package com.shoporders.domain

data class Item(
    val name: String,
    val qty: Long,
)

data class Order(
    val items: List<Item>,
)
