package com.baeldung.kovert

import com.fasterxml.jackson.annotation.JsonProperty
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

class JsonServer {
    companion object {
        private val LOG: Logger = LoggerFactory.getLogger(JsonServer::class.java)

        @JvmStatic
        fun main(args: Array<String>) {
            JsonServer().start()
        }
    }

    data class Person(
            @JsonProperty("_id")
            val id: String,
            val name: String,
            val job: String
    )

    class JsonController {
        fun RoutingContext.getPersonById(id: String) = Person(
                id = id,
                name = "Tony Stark",
                job = "Iron Man"
        )
        fun RoutingContext.putPersonById(id: String, person: Person) = person
    }

    fun start() {
        Kodein.global.addImport(Kodein.Module {
            importConfig(loadConfig(ClassResourceConfig("/kovert.conf", JsonServer::class.java), ReferenceConfig())) {
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
            bindController(JsonController(), "api")
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
