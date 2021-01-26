package com.shoporders.controllers

import com.shoporders.domain.Item
import com.shoporders.services.ItemService
import io.micronaut.http.annotation.*
import mu.KotlinLogging


private val log = KotlinLogging.logger {}

@Controller("/shoporder/item")
class ItemController(private val itemService: ItemService) {

    @Get("/{id}")
    fun fetch(@PathVariable id: String): Item {
        log.debug("fetch Item with id = $id")
        return itemService.fetch(id)
    }

    @Post
    fun save(item: Item): Item {
        log.debug("save $item")
        return itemService.save(item)
    }

    @Delete("/{id}")
    fun delete(@PathVariable id: String) {
        log.debug("delete item id=$id")
        return itemService.delete(id)
    }

}