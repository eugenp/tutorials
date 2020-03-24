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
import uy.kohesive.kovert.core.HttpVerb
import uy.kohesive.kovert.core.Location
import uy.kohesive.kovert.core.Verb
import uy.kohesive.kovert.core.VerbAlias
import uy.kohesive.kovert.vertx.bindController
import uy.kohesive.kovert.vertx.boot.KodeinKovertVertx
import uy.kohesive.kovert.vertx.boot.KovertVerticle
import uy.kohesive.kovert.vertx.boot.KovertVerticleModule
import uy.kohesive.kovert.vertx.boot.KovertVertx


class AnnotatedServer {
    companion object {
        private val LOG: Logger = LoggerFactory.getLogger(AnnotatedServer::class.java)

        @JvmStatic
        fun main(args: Array<String>) {
            AnnotatedServer().start()
        }
    }

    @VerbAlias("show", HttpVerb.GET)
    class AnnotatedController {
        fun RoutingContext.showStringById(id: String) = id

        @Verb(HttpVerb.GET)
        @Location("/ping/:id")
        fun RoutingContext.ping(id: String) = id
    }

    fun start() {
        Kodein.global.addImport(Kodein.Module {
            importConfig(loadConfig(ClassResourceConfig("/kovert.conf", AnnotatedServer::class.java), ReferenceConfig())) {
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
            bindController(AnnotatedController(), "api")
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
