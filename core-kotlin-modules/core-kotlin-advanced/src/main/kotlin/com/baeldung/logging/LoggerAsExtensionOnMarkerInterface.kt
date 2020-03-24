package com.baeldung.logging

import org.slf4j.Logger
import org.slf4j.LoggerFactory

interface Logging

inline fun <reified T : Logging> T.logger(): Logger =
        //Wrong logger name!
        //LoggerFactory.getLogger(javaClass.name + " w/interface")
        LoggerFactory.getLogger(getClassForLogging(T::class.java).name + " w/interface")

open class LoggerAsExtensionOnMarkerInterface : Logging {
    companion object : Logging {
        val logger = logger()
    }

    fun log(s: String) {
        logger().info(s)
        logger.info(s)
    }
}

class MarkerExtensionSubclass : LoggerAsExtensionOnMarkerInterface()

fun main(args: Array<String>) {
    LoggerAsExtensionOnMarkerInterface().log("test")
    MarkerExtensionSubclass().log("sub")
    "foo".logger().info("foo")
}