package com.baeldung.constructor

open class Person(
        val name: String,
        val age: Int? = null
) {
    val upperCaseName: String = name.toUpperCase()

    init {
        println("Hello, I'm $name")

        if (age != null && age < 0) {
            throw IllegalArgumentException("Age cannot be less than zero!")
        }
    }

    init {
        println("upperCaseName is $upperCaseName")
    }

}

fun main(args: Array<String>) {
    val person = Person("John")
    val personWithAge = Person("John", 22)
}