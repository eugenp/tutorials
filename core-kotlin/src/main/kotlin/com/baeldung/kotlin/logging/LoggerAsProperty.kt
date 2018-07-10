package com.baeldung.kotlin.logging

class LoggerAsProperty {
    private val logger = logger(javaClass)

    fun log(s: String) {
        logger.info(s)
    }

}

fun main(args: Array<String>) {
    LoggerAsProperty().log("test")
}