package com.baeldung.injekt

import org.slf4j.LoggerFactory
import uy.kohesive.injekt.Injekt
import uy.kohesive.injekt.InjektMain
import uy.kohesive.injekt.api.InjektRegistrar
import uy.kohesive.injekt.api.addSingleton
import uy.kohesive.injekt.api.addSingletonFactory
import uy.kohesive.injekt.api.get

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
