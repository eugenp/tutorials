@file:JvmName("APIServer")

import com.google.gson.Gson
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.features.DefaultHeaders
import io.ktor.http.ContentType
import io.ktor.request.path
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import org.slf4j.event.Level

fun main(args: Array<String>) {

    val jsonResponse = """{
            "id": 1,
            "task": "Pay waterbill",
            "description": "Pay water bill today",
        }"""

    data class Author(val name: String, val website: String)

    embeddedServer(Netty, 8080) {
        install(DefaultHeaders) {
            header("X-Developer", "Baeldung")
        }
        install(CallLogging) {
            level = Level.INFO
            filter { call -> call.request.path().startsWith("/todo") }
            filter { call -> call.request.path().startsWith("/author") }
        }
        routing {
            get("/todo") {
                call.respondText(jsonResponse, ContentType.Application.Json)
            }
            get("/author") {
                val author = Author("baeldung", "baeldung.com")
                val gson = Gson()
                val json = gson.toJson(author)
                call.respondText(json, ContentType.Application.Json)

            }

        }
    }.start(wait = true)
}