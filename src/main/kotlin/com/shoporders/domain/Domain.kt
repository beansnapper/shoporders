package com.shoporders.domain

data class Item(
    val name : String
)

data class Order(
    val items : List<Item>,
)
