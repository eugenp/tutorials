@file:JvmName("APIServer")



import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.features.DefaultHeaders
import io.ktor.gson.gson
import io.ktor.http.ContentType
import io.ktor.request.path
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import org.slf4j.event.Level

data class Author(val name: String, val website: String)
fun main(args: Array<String>) {

    val jsonResponse = """{
            "id": 1,
            "task": "Pay waterbill",
            "description": "Pay water bill today",
        }"""


    embeddedServer(Netty, 8080) {
        install(DefaultHeaders) {
            header("X-Developer", "Baeldung")
        }
        install(CallLogging) {
            level = Level.INFO
            filter { call -> call.request.path().startsWith("/todo") }
            filter { call -> call.request.path().startsWith("/author") }
        }
        install(ContentNegotiation) {
            gson {
                setPrettyPrinting()
            }
        }
        routing {
            get("/todo") {
                call.respondText(jsonResponse, ContentType.Application.Json)
            }
            get("/author") {
                val author = Author("baeldung", "baeldung.com")
                call.respond(author)

            }

        }
    }.start(wait = true)
}