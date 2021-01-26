package com.shoporders.persistence

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoCollection
import com.shoporders.OrderException
import com.shoporders.domain.Item
import com.shoporders.domain.Timestamp
import com.shoporders.persistence.MongoUtils.queryById
import com.shoporders.persistence.MongoUtils.queryByIdTimestamp
import com.shoporders.persistence.MongoUtils.toObjectId
import mu.KotlinLogging
import org.bson.BsonDateTime
import org.bson.Document
import org.bson.types.ObjectId
import java.util.*
import javax.inject.Singleton

private val log = KotlinLogging.logger {}

/**
 * Note: there's a lot of boilerplate here that I've been working on an
 * open source project to generate.
 */

@Singleton
class ItemDataService(val mongoClient: MongoClient) {

    private fun getCollection(): MongoCollection<Document?> {
        return mongoClient
            .getDatabase("ShopOrder")
            .getCollection("items")
    }

    private fun read(document: Document): Item {
        return Item(
            (document["_id"] as ObjectId?)!!.toHexString(),
            document["timestamp"] as Date?,
            document["name"] as String,
            document["cost"] as Long
        )
    }

    private fun write(item: Item): Document {
        val doc = Document()
        doc.append("_id", toObjectId(item.id))
        doc.append("timestamp", BsonDateTime(System.currentTimeMillis()))
        doc.append("name", item.name)
        doc.append("cost", item.cost)
        return doc
    }

    fun fetch(id: String): Item {
        return try {
            val document = getCollection().find(queryById(id)).first()
            if (document == null) {
                val msg = "Not found: Item(id = $id)"
                log.debug(msg)
                throw OrderException(msg)
            }
            val item = read(document)
            log.debug("fetched {}", item)
            item
        } catch (e: Exception) {
            val msg = "Exception caught fetching Item (id = $id)"
            log.debug(msg, e)
            throw OrderException(msg, e)
        }
    }

    fun insert(item: Item): Item {
        return try {
            if (item.id != null) {
                // safeguard
                val msg = "Cannot insert item with non-null id = ${item.id}"
                log.debug(msg)
                throw OrderException(msg)
            }
            val timestamped = item.copy(timestamp = Timestamp())
            val newId = getCollection().insertOne(write(timestamped)).insertedId
            if (newId == null) {
                val msg = "Unable to insert Item $item - no id generated check mongo logs"
                log.debug(msg)
                throw OrderException(msg)
            }
            val insertedItem = (timestamped.copy(id = newId.asObjectId().value.toHexString()))
            log.debug("Inserted Item {}", insertedItem)
            insertedItem
        } catch (e: Exception) {
            val msg = "Caught exception inserting item: $item"
            log.debug(msg, e)
            throw OrderException(msg, e)
        }
    }

    fun update(item: Item): Item {
        return try {
            if (item.id == null || item.timestamp == null) {
                val msg = "Cannot update item with null id or timestamp: $item"
                log.debug(msg)
                throw OrderException(msg)
            }
            val query = queryByIdTimestamp(item.id, item.timestamp)
            val updatedItem = item.copy(timestamp = Timestamp())
            getCollection().findOneAndReplace(query, write(updatedItem))
            updatedItem
        } catch (e: Exception) {
            val msg = "Caught exception updating item (possible dirty write): $item"
            log.debug(msg, e)
            throw OrderException(msg, e)
        }
    }


    fun delete(id: String) {
        try {
            getCollection().deleteOne(queryById(id))
        } catch (e: Exception) {
            val msg = "Unable to delete Item(id = $id)"
            log.debug(msg)
            throw OrderException(msg, e)
        }
    }


}