package com.baeldung.kovert

import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import nl.komponents.kovenant.functional.bind
import org.kodein.di.Kodein
import org.kodein.di.conf.global
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import uy.klutter.config.typesafe.ClassResourceConfig
import uy.klutter.config.typesafe.ReferenceConfig
import uy.klutter.config.typesafe.kodein.importConfig
import uy.klutter.config.typesafe.loadConfig
import uy.klutter.vertx.kodein.KodeinVertx
import uy.kohesive.kovert.vertx.bindController
import uy.kohesive.kovert.vertx.boot.KodeinKovertVertx
import uy.kohesive.kovert.vertx.boot.KovertVerticle
import uy.kohesive.kovert.vertx.boot.KovertVerticleModule
import uy.kohesive.kovert.vertx.boot.KovertVertx


class SimpleServer {
    companion object {
        private val LOG: Logger = LoggerFactory.getLogger(SimpleServer::class.java)

        @JvmStatic
        fun main(args: Array<String>) {
            SimpleServer().start()
        }
    }

    class SimpleController {
        fun RoutingContext.getStringById(id: String) = id
        fun RoutingContext.get_truncatedString_by_id(id: String, length: Int = 1) = id.subSequence(0, length)
    }

    fun start() {
        Kodein.global.addImport(Kodein.Module {
            importConfig(loadConfig(ClassResourceConfig("/kovert.conf", SimpleServer::class.java), ReferenceConfig())) {
                import("kovert.vertx", KodeinKovertVertx.configModule)
                import("kovert.server", KovertVerticleModule.configModule)
            }

            // includes jackson ObjectMapper to match compatibility with Vertx, app logging via Vertx facade to Slf4j
            import(KodeinVertx.moduleWithLoggingToSlf4j)
            // Kovert boot
            import(KodeinKovertVertx.module)
            import(KovertVerticleModule.module)
        })

        val initControllers = fun Router.() {
            bindController(SimpleController(), "api")
        }

        // startup asynchronously...
        KovertVertx.start() bind { vertx ->
            KovertVerticle.deploy(vertx, routerInit = initControllers)
        } success { deploymentId ->
            LOG.warn("Deployment complete.")
        } fail { error ->
            LOG.error("Deployment failed!", error)
        }

    }
}
