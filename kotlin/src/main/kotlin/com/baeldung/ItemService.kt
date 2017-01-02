package com.baeldung

import java.util.*

class ItemService {
    fun findItemNameForId(id: String): Item? {
        val itemId = UUID.randomUUID().toString()
        return Item(itemId, "name-$itemId");
    }
}