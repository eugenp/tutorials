package veontomo

import com.baeldung.springreactivekotlin.SimpleRoute
import org.junit.Before
import org.junit.Test
import org.springframework.test.web.reactive.server.WebTestClient

class RoutesTest {

    lateinit var client: WebTestClient

    @Before
    fun init() {
        this.client = WebTestClient.bindToRouterFunction(SimpleRoute().route()).build()
    }


    @Test
    fun whenRequestToRoute_thenStatusShouldBeOk() {
        client.get()
                .uri("/route")
                .exchange()
                .expectStatus().isOk
    }


    @Test
    fun whenRequestToRoute_thenBodyShouldContainArray123() {
        client.get()
                .uri("/route")
                .exchange()
                .expectBody()
                .json("[1, 2, 3]")
    }
}