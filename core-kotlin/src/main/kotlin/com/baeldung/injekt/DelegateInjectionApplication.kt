package com.baeldung.injekt

import org.slf4j.LoggerFactory
import uy.kohesive.injekt.*
import uy.kohesive.injekt.api.*
import java.util.*

class DelegateInjectionApplication {
    companion object : InjektMain() {
        private val LOG = LoggerFactory.getLogger(DelegateInjectionApplication::class.java)
        @JvmStatic fun main(args: Array<String>) {
            DelegateInjectionApplication().run()
        }

        override fun InjektRegistrar.registerInjectables() {
            addFactory {
                val value = FactoryInstance("Factory" + UUID.randomUUID().toString())
                LOG.info("Constructing instance: {}", value)
                value
            }

            addSingletonFactory {
                val value = SingletonInstance("Singleton" + UUID.randomUUID().toString())
                LOG.info("Constructing singleton instance: {}", value)
                value
            }

            addSingletonFactory { App() }
        }
    }

    data class FactoryInstance(val value: String)
    data class SingletonInstance(val value: String)

    class App {
        private val instance: FactoryInstance by injectValue()
        private val lazyInstance: FactoryInstance by injectLazy()
        private val singleton: SingletonInstance by injectValue()
        private val lazySingleton: SingletonInstance by injectLazy()

        fun run() {
            for (i in 1..5) {
                LOG.info("Instance {}: {}", i, instance)
            }
            for (i in 1..5) {
                LOG.info("Lazy Instance {}: {}", i, lazyInstance)
            }
            for (i in 1..5) {
                LOG.info("Singleton {}: {}", i, singleton)
            }
            for (i in 1..5) {
                LOG.info("Lazy Singleton {}: {}", i, lazySingleton)
            }
        }
    }

    fun run() {
        Injekt.get<App>().run()
    }
}
