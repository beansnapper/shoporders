package com.shoporders.services

import com.shoporders.domain.Item
import com.shoporders.persistence.ItemDataService
import javax.inject.Singleton

@Singleton
class ItemService(val itemDataService: ItemDataService) {

    fun fetch(id: String): Item {
        return itemDataService.fetch(id)
    }

    fun save(item: Item): Item {
        return if (item.id == null) {
            itemDataService.insert(item)
        } else {
            itemDataService.update(item)
        }
    }

    fun delete(id: String) {
        itemDataService.delete(id)
    }

}