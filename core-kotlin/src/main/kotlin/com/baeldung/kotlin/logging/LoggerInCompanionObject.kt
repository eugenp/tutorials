package com.baeldung.kotlin.logging

class LoggerInCompanionObject {
    companion object {
        private val loggerWithExplicitClass = logger(LoggerInCompanionObject::class.java)
        @Suppress("JAVA_CLASS_ON_COMPANION")
        private val loggerWithWrongClass = logger(javaClass)
        @Suppress("JAVA_CLASS_ON_COMPANION")
        private val logger = logger(javaClass.enclosingClass)
    }

    fun log(s: String) {
        loggerWithExplicitClass.info(s)
        loggerWithWrongClass.info(s)
        logger.info(s)
    }

    class Inner {
        companion object {
            private val loggerWithExplicitClass = logger(Inner::class.java)
            @Suppress("JAVA_CLASS_ON_COMPANION")
            @JvmStatic
            private val loggerWithWrongClass = logger(javaClass)
            @Suppress("JAVA_CLASS_ON_COMPANION")
            @JvmStatic
            private val logger = logger(javaClass.enclosingClass)
        }

        fun log(s: String) {
            loggerWithExplicitClass.info(s)
            loggerWithWrongClass.info(s)
            logger.info(s)
        }
    }

}

fun main(args: Array<String>) {
    LoggerInCompanionObject().log("test")
    LoggerInCompanionObject.Inner().log("test")
}