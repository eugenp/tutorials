package com.baeldung.logging

open class LoggerAsProperty {
    private val logger = getLogger(javaClass)

    fun log(s: String) {
        logger.info(s)
    }

}

class PropertySubclass : LoggerAsProperty()

fun main(args: Array<String>) {
    LoggerAsProperty().log("test")
    PropertySubclass().log("sub")
}