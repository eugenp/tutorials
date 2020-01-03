package com.baeldung.logging

open class LoggerInCompanionObject {
    companion object {
        private val loggerWithExplicitClass = getLogger(LoggerInCompanionObject::class.java)
        @Suppress("JAVA_CLASS_ON_COMPANION")
        private val loggerWithWrongClass = getLogger(javaClass)
        @Suppress("JAVA_CLASS_ON_COMPANION")
        private val logger = getLogger(javaClass.enclosingClass)
    }

    fun log(s: String) {
        loggerWithExplicitClass.info(s)
        loggerWithWrongClass.info(s)
        logger.info(s)
    }

    class Inner {
        companion object {
            private val loggerWithExplicitClass = getLogger(Inner::class.java)
            @Suppress("JAVA_CLASS_ON_COMPANION")
            @JvmStatic
            private val loggerWithWrongClass = getLogger(javaClass)
            @Suppress("JAVA_CLASS_ON_COMPANION")
            @JvmStatic
            private val logger = getLogger(javaClass.enclosingClass)
        }

        fun log(s: String) {
            loggerWithExplicitClass.info(s)
            loggerWithWrongClass.info(s)
            logger.info(s)
        }
    }

}

class CompanionSubclass : LoggerInCompanionObject()

fun main(args: Array<String>) {
    LoggerInCompanionObject().log("test")
    LoggerInCompanionObject.Inner().log("test")
    CompanionSubclass().log("sub")
}