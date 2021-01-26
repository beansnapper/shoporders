package com.shoporders.persistence

import org.bson.BsonDateTime
import org.bson.BsonObjectId
import org.bson.Document
import org.bson.types.ObjectId
import java.util.*

object MongoUtils {

    fun queryById(hexString: String?): Document {
        return Document("_id", BsonObjectId(ObjectId(hexString)))
    }

    fun queryByIdTimestamp(hexString: String?, date: Date): Document {
        return Document()
            .append("_id", BsonObjectId(ObjectId(hexString)))
            .append("timestamp", BsonDateTime(date.time))
    }

    fun toObjectId(hexString: String?): BsonObjectId? {
        return BsonObjectId(hexString?.let { ObjectId(it) } ?: ObjectId())
    }

}