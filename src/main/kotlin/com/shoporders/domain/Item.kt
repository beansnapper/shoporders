package com.shoporders.domain

data class Item(
    val id: String? = null,
    val timestamp: Timestamp? = null,
    val name: String,
    val cost: Long,
)