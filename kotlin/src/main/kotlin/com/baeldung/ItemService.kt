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

    var concat = ""
    for (n in numbers) {
        concat += n
    }

    var sum = 0
    for (i in 2..9) {
        sum += i
    }

    val firstName = "Tom"
    val secondName = "Mary"
    val concatOfNames = "$firstName + $secondName"
    println("Names: $concatOfNames")

    val itemManager = ItemManager("cat_id", "db://connection")
    val result = "function result: ${itemManager.isFromSpecificCategory("1")}"
    println(result)

    val number = 2
    if (number < 10) {
        println("number less that 10")
    } else if (number > 10) {
        println("number is greater that 10")
    }

    val name = "John"
    when (name) {
        "John" -> println("Hi man")
        "Alice" -> println("Hi lady")
        else -> {
            println("Hi")
        }
    }

}