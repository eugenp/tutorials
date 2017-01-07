package com.baeldung

import java.util.*

class ItemService {
    fun findItemNameForId(id: String): Item? {
        val itemId = UUID.randomUUID().toString()
        return Item(itemId, "name-$itemId")
    }
}

class ItemManager(val categoryId: String, val dbConnection: String) {
    var email = ""

    constructor(categoryId: String, dbConnection: String, email: String)
    : this(categoryId, dbConnection) {
        this.email = email
    }

    fun isFromSpecificCategory(catId: String): Boolean {
        return categoryId == catId
    }

    fun makeAnalyisOfCategory(catId: String): Unit {
        val result = if (catId == "100") "Yes" else "No"
        println(result)

    }
}

fun main(args: Array<String>) {
    val numbers = arrayOf("first", "second", "third", "fourth")

    for (n in numbers) {
        println(n)
    }

    for (i in 0..9) {
        println(i)
    }

    val firstName = "Tom"
    val secondName = "Mary"
    println("Names: $firstName, $secondName")

    val itemManager = ItemManager("cat_id", "db://connection")
    print("function result: ${itemManager.isFromSpecificCategory("1")}")
}