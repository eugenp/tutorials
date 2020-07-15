package com.baeldung.kotlinwiremock

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.core.WireMockConfiguration
import com.marcinziolo.kotlin.wiremock.contains
import com.marcinziolo.kotlin.wiremock.equalTo
import com.marcinziolo.kotlin.wiremock.get
import com.marcinziolo.kotlin.wiremock.like
import com.marcinziolo.kotlin.wiremock.post
import com.marcinziolo.kotlin.wiremock.returns
import com.marcinziolo.kotlin.wiremock.returnsJson
import io.restassured.module.kotlin.extensions.Extract
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.net.ServerSocket
import kotlin.test.assertEquals

class JUnitIntegrationTest {
    private val port = findOpenPort()
    val wiremock: WireMockServer
    val url get() = "http://localhost:$port"

    init {
        wiremock = WireMockServer(WireMockConfiguration.options().port(port))
    }

    @BeforeEach
    internal fun setUp() {
        wiremock.start()
    }

    @AfterEach
    internal fun tearDown() {
        wiremock.stop()
    }

    private fun findOpenPort(): Int {
        ServerSocket(0).use { socket -> return socket.localPort }
    }

    @Test
    internal fun givenJUnitManagedServer_whenMatchingURL_thenCorrect() {
        wiremock.get {
            url like "/baeldung/.*"
        } returnsJson {
            body = """ {"testing-library": "WireMock"} """
        }

        When {
            get("$url/baeldung/wiremock")
        } Then {
            statusCode(200)
            body("testing-library", equalTo("WireMock"))
        }
    }

    @Test
    internal fun givenJUnitManagedServer_whenMatchingHeaders_thenCorrect() {
        wiremock.get {
            url equalTo  "/baeldung/wiremock"
            headers contains "Accept" like "text/.*"
        } returns  {
            statusCode = 503
            header = "Content-Type" to "text/html"
            body = "!!! Service Unavailable !!!"
        }

        Given {
            header("Accept", "text/html")
        } When {
            get("$url/baeldung/wiremock")
        } Then {
            statusCode(503)
            header("Content-Type", equalTo("text/html"))
            body("html.body", equalTo("!!! Service Unavailable !!!"))
        }
    }

    @Test
    internal fun givenJUnitManagedServer_whenMatchingBody_thenCorrect() {
        wiremock.post {
            url equalTo  "/baeldung/wiremock"
            headers contains "Content-Type" contains "application/json"
            body contains "testing-library" equalTo "WireMock"
            body contains "creator" contains "Tom"
            body contains "website" contains "wiremock.[org|com]"
        } returns  {
            statusCode = 200
        }

        Given {
            header("Content-Type", "application/json")
            body("""
                {
                	"testing-library": "WireMock",
                	"creator": "Tom Akehurst",
                	"website": "wiremock.org"
                }
            """.trimIndent())
        } When {
            post("$url/baeldung/wiremock")
        } Then {
            statusCode(200)
        }
    }

    @Test
    internal fun givenJUnitManagedServer_whenNotUsingPriority_thenCorrect() {
        wiremock.get {
            url like  "/baeldung/.*"
        } returns  {
            statusCode = 200
        }

        wiremock.get {
            url like  "/baeldung/wiremock"
            headers contains "Accept" like "text/.*"
        } returns  {
            statusCode = 503
        }

        Given {
            header("Accept", "text/html")
        } When {
            get("$url/baeldung/wiremock")
        } Then {
            statusCode(503)
        }
    }

    @Test
    internal fun generateClientAndReceiveResponseForPriorityTests() {
        wiremock.get {
            url like  "/baeldung/.*"
            priority = 1
        } returns  {
            statusCode = 200
        }

        wiremock.get {
            url like  "/baeldung/wiremock"
            headers contains "Accept" like "text/.*"
            priority = 2
        } returns  {
            statusCode = 503
        }

        Given {
            header("Accept", "text/html")
        } When {
            get("$url/baeldung/wiremock")
        } Then {
            statusCode(200)
        }
    }

    @Test
    internal fun givenScenarioWhenMatchingThenCorrect() {
        wiremock.get {
            url equalTo   "/java-tip"
        } returns  {
            body = "finally block is not called when System.exit() is called in the try block"
            toState = "tip2"
        }

        wiremock.get {
            url equalTo   "/java-tip"
            whenState = "tip2"
        } returns  {
            body = "keep your code clean"
            toState = "tip3"
        }

        wiremock.get {
            url equalTo "/java-tip"
            whenState = "tip3"
        } returns  {
            body = "use composition rather than inheritance"
            clearState = true
        }

        val responses = sequence {
            for(i in 1 .. 4) {
                yield(When {
                    get("$url/java-tip")
                } Extract {
                    body().asString()
                })
            }
        }.take(4).toList()

        assertEquals("finally block is not called when System.exit() is called in the try block", responses[0])
        assertEquals("keep your code clean", responses[1])
        assertEquals("use composition rather than inheritance", responses[2])
        assertEquals("finally block is not called when System.exit() is called in the try block", responses[3])
    }
}