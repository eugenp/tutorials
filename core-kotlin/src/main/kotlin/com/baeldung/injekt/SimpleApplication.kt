package com.baeldung.injekt

import org.slf4j.LoggerFactory
import uy.kohesive.injekt.*
import uy.kohesive.injekt.api.*

class SimpleApplication {
    companion object : InjektMain() {
        private val LOG = LoggerFactory.getLogger(Server::class.java)
        @JvmStatic fun main(args: Array<String>) {
            SimpleApplication().run()
        }

        override fun InjektRegistrar.registerInjectables() {
            addSingleton(Config(12345))
            addSingletonFactory { Server(Injekt.get()) }
        }
    }

    data class Config(
            val port: Int
    )

    class Server(private val config: Config) {

        fun start() {
            LOG.info("Starting server on ${config.port}")
        }
    }

    fun run() {
        Injekt.get<Server>().start()
    }
}
