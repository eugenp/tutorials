package com.baeldung.constructor

class Car {
    val id: String
    val type: String

    constructor(id: String, type: String) {
        this.id = id
        this.type = type
    }

}

fun main(args: Array<String>) {
    val car = Car("1", "sport")
    val s= Car("2", "suv")
}