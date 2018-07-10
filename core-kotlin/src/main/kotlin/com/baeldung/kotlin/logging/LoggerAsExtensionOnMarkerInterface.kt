package com.baeldung.kotlin.logging

import org.slf4j.Logger
import org.slf4j.LoggerFactory

interface Logging

inline fun <reified T : Logging> T.logger(): Logger =
        //Wrong logger name!
        //LoggerFactory.getLogger(javaClass.name + " w/interface")
        LoggerFactory.getLogger(getClassForLogging(T::class.java).name + " w/interface")

class LoggerAsExtensionOnMarkerInterface : Logging {
    companion object : Logging {
        val logger = logger()
    }

    fun log(s: String) {
        logger().info(s)
        logger.info(s)
    }
}

fun main(args: Array<String>) {
    LoggerAsExtensionOnMarkerInterface().log("test")
    "foo".logger().info("foo")
}