package io.exera.quotz.database

import io.realm.*
import java.util.*

class MyMigration : RealmMigration {
    override fun migrate(realm: DynamicRealm?, oldVersion: Long, newVersion: Long) {
        val schema: RealmSchema = realm!!.schema

        if (oldVersion == 0L) {
            val quote = schema.get("Quote")
            quote!!.addField("isFavorite", String::class.java, FieldAttribute.REQUIRED)
                    .transform({
                        it.set("isFavorite", false)
                    })
        }
        if (oldVersion == 1L) {
            val quote = schema.get("Quote")
            quote!!.addField("id", String::class.java, FieldAttribute.REQUIRED)
                    .transform({
                        it.set("id", UUID.randomUUID().toString())
                    })
        }
    }
}