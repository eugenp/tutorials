package com.baeldung.springbootkotlin

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest(
  classes = arrayOf(KotlinDemoApplication::class),
  webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class KotlinDemoApplicationIntegrationTest {

	@Autowired
	lateinit var testRestTemplate: TestRestTemplate

	@Test
	fun whenCalled_thenShouldReturnHello() {
		val result = testRestTemplate.withBasicAuth("user", "pass")
                .getForEntity("/hello", String::class.java)

		assertNotNull(result)
		assertEquals(HttpStatus.OK, result?.statusCode)
		assertEquals("hello world", result?.body)
	}

	@Test
	fun whenCalled_thenShouldReturnHelloService() {
		val result = testRestTemplate.withBasicAuth("user", "pass")
                .getForEntity("/hello-service", String::class.java)

		assertNotNull(result)
		assertEquals(HttpStatus.OK, result?.statusCode)
		assertEquals(result?.body, "hello service")
	}

	@Test
	fun whenCalled_thenShouldReturnJson() {
		val result = testRestTemplate.withBasicAuth("user", "pass")
                .getForEntity("/hello-dto", HelloDto::class.java)

		assertNotNull(result)
		assertEquals(HttpStatus.OK, result?.statusCode)
		assertEquals(result?.body, HelloDto("Hello from the dto"))
	}

}
