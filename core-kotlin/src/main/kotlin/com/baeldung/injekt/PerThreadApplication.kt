package com.baeldung.injekt

import org.slf4j.LoggerFactory
import uy.kohesive.injekt.*
import uy.kohesive.injekt.api.*
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class PerThreadApplication {
    companion object : InjektMain() {
        private val LOG = LoggerFactory.getLogger(PerThreadApplication::class.java)
        @JvmStatic fun main(args: Array<String>) {
            PerThreadApplication().run()
        }

        override fun InjektRegistrar.registerInjectables() {
            addPerThreadFactory {
                val value = FactoryInstance(UUID.randomUUID().toString())
                LOG.info("Constructing instance: {}", value)
                value
            }

            addSingletonFactory { App() }
        }
    }

    data class FactoryInstance(val value: String)

    class App {
        fun run() {
            val threadPool = Executors.newFixedThreadPool(5)

            for (i in 1..20) {
                threadPool.submit {
                    val instance = Injekt.get<FactoryInstance>()
                    LOG.info("Value for thread {}: {}", Thread.currentThread().id, instance)
                    TimeUnit.MILLISECONDS.sleep(100)
                }
            }
            threadPool.awaitTermination(10, TimeUnit.SECONDS)
        }
    }

    fun run() {
        Injekt.get<App>().run()
    }
}
