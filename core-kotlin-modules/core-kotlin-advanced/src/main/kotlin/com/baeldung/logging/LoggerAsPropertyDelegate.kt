package com.baeldung.logging

import org.slf4j.Logger
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

open class LoggerAsPropertyDelegate {
    private val lazyLogger by lazyLogger()
    protected val logger by LoggerDelegate()
    private val logger2 = logger

    companion object {
        private val lazyLoggerComp by lazyLogger()
        private val loggerComp by LoggerDelegate()
    }

    open fun log(s: String) {
        logger.info(s)
        logger2.info(s)
        lazyLogger.info(s)
        loggerComp.info(s)
        lazyLoggerComp.info(s)
    }

}

class DelegateSubclass : LoggerAsPropertyDelegate() {
    override fun log(s: String) {
        logger.info("-- in sub")
        super.log(s)
    }
}

fun lazyLogger(forClass: Class<*>): Lazy<Logger> =
        lazy { getLogger(getClassForLogging(forClass)) }

fun <T : Any> T.lazyLogger(): Lazy<Logger> = lazyLogger(javaClass)

fun main(args: Array<String>) {
    LoggerAsPropertyDelegate().log("test")
    DelegateSubclass().log("sub")
}

class LoggerDelegate<in R : Any> : ReadOnlyProperty<R, Logger> {
    override fun getValue(thisRef: R, property: KProperty<*>) =
            getLogger(getClassForLogging(thisRef.javaClass))
}
