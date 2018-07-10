package com.baeldung.kotlin.logging

import org.slf4j.Logger
import org.slf4j.LoggerFactory

class LoggerAsExtensionOnAny {
    val logger = logger()

    fun log(s: String) {
        logger().info(s)
        logger.info(s)
    }
}

fun logger(forClass: Class<*>): Logger = LoggerFactory.getLogger(forClass)

fun <T : Any> T.logger(): Logger = logger(getClassForLogging(javaClass))

fun main(args: Array<String>) {
    LoggerAsExtensionOnAny().log("test")
    "foo".logger().info("foo")
    1.logger().info("uh-oh!")
    SomeOtherClass().logger()
}

class SomeOtherClass {
    fun logger(): String {
        return "foo"
    }
}