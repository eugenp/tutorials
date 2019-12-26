package com.baeldung.injekt

import org.slf4j.LoggerFactory
import uy.kohesive.injekt.*
import uy.kohesive.injekt.api.*

class ModularApplication {
    class ConfigModule(private val port: Int) : InjektModule {
        override fun InjektRegistrar.registerInjectables() {
            addSingleton(Config(port))
        }
    }

    object ServerModule : InjektModule {
        override fun InjektRegistrar.registerInjectables() {
            addSingletonFactory { Server(Injekt.get()) }
        }
    }

    companion object : InjektMain() {
        private val LOG = LoggerFactory.getLogger(Server::class.java)
        @JvmStatic fun main(args: Array<String>) {
            ModularApplication().run()
        }

        override fun InjektRegistrar.registerInjectables() {
            importModule(ConfigModule(12345))
            importModule(ServerModule)
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
