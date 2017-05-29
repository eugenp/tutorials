package com.example.kotlindemo

import com.baeldung.springbootkotlin.HelloPoko
import com.baeldung.springbootkotlin.KotlinDemoApplication
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest(classes = arrayOf(KotlinDemoApplication::class), webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class KotlinDemoApplicationTests {

	@Autowired
	val testRestTemplate: TestRestTemplate? = null

	@Test
	fun contextLoads() {
	}

	@Test
	fun testHelloController() {
		var result = testRestTemplate?.getForEntity("/hello", String::class.java)

		Assert.assertNotNull(result)
		Assert.assertEquals(result?.statusCode, HttpStatus.OK)
		Assert.assertEquals(result?.body, "hello world")
	}

	@Test
	fun testHelloService() {
		var result = testRestTemplate?.getForEntity("/hello-service", String::class.java)

		Assert.assertNotNull(result)
		Assert.assertEquals(result?.statusCode, HttpStatus.OK)
		Assert.assertEquals(result?.body, "hello service")
	}

	@Test
	fun testHelloPoko() {
		var result = testRestTemplate?.getForEntity("/hello-poko", HelloPoko::class.java)

		Assert.assertNotNull(result)
		Assert.assertEquals(result?.statusCode, HttpStatus.OK)
		Assert.assertEquals(result?.body, HelloPoko("Hello from the poko"))
	}

}
