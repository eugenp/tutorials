package com.baeldung.kotlin.logging

import org.slf4j.Logger
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class LoggerAsPropertyDelegate {
    private val lazyLogger by lazyLogger()
    private val logger by LoggerDelegate()
    private val logger2 = logger

    companion object {
        private val lazyLoggerComp by lazyLogger()
        private val loggerComp by LoggerDelegate()
    }

    fun log(s: String) {
        logger.info(s)
        logger2.info(s)
        lazyLogger.info(s)
        loggerComp.info(s)
        lazyLoggerComp.info(s)
    }

}

fun lazyLogger(forClass: Class<*>): Lazy<Logger> =
        lazy { logger(getClassForLogging(forClass)) }

fun <T : Any> T.lazyLogger(): Lazy<Logger> = lazyLogger(javaClass)

fun main(args: Array<String>) {
    LoggerAsPropertyDelegate().log("test")
}

class LoggerDelegate<in R : Any> : ReadOnlyProperty<R, Logger> {
    override fun getValue(thisRef: R, property: KProperty<*>) =
        logger(getClassForLogging(thisRef.javaClass))
}
