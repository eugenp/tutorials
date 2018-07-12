package com.baeldung.kotlin.logging

open class LoggerAsProperty {
    private val logger = logger(javaClass)

    fun log(s: String) {
        logger.info(s)
    }

}

class PropertySubclass : LoggerAsProperty()

fun main(args: Array<String>) {
    LoggerAsProperty().log("test")
    PropertySubclass().log("sub")
}