package com.baeldung.kotlin.logging

import org.slf4j.Logger
import org.slf4j.LoggerFactory

open class LoggerAsExtensionOnAny {
    val logger = logger()

    fun log(s: String) {
        logger().info(s)
        logger.info(s)
    }
}

class ExtensionSubclass : LoggerAsExtensionOnAny()

fun <T : Any> T.logger(): Logger = logger(getClassForLogging(javaClass))

fun main(args: Array<String>) {
    LoggerAsExtensionOnAny().log("test")
    ExtensionSubclass().log("sub")
    "foo".logger().info("foo")
    1.logger().info("uh-oh!")
    SomeOtherClass().logger()
}

class SomeOtherClass {
    fun logger(): String {
        return "foo"
    }
}