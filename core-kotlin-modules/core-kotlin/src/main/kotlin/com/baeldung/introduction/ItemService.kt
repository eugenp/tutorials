package com.baeldung.introduction

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
        `object`()
    }

    fun sum(a: Int, b: Int): Int {
        return a + b
    }

    fun `object`(): String {
        return "this is object"
    }

}

fun main(args: Array<String>) {
    val numbers = arrayOf("first", "second", "third", "fourth")

    for (n in numbers) {
        println(n)
    }

    for (i in 2..9 step 2) {
        println(i)
    }

    val res = 1.rangeTo(10).map { it * 2 }
    println(res)

    val firstName = "Tom"
    val secondName = "Mary"
    val concatOfNames = "$firstName + $secondName"
    println("Names: $concatOfNames")
    val sum = "four: ${2 + 2}"

    val itemManager = ItemManager("cat_id", "db://connection")
    ItemManager(categoryId = "catId", dbConnection = "db://Connection")
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
    }

    val items = listOf(1, 2, 3, 4)


    val rwList = mutableListOf(1, 2, 3)
    rwList.add(5)
}