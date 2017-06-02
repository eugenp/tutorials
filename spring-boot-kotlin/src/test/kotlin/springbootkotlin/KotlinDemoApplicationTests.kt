package springbootkotlin

import com.baeldung.springbootkotlin.HelloDto
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
		val result = testRestTemplate?.getForEntity("/hello", String::class.java)

		Assert.assertNotNull(result)
		Assert.assertEquals(result?.statusCode, HttpStatus.OK)
		Assert.assertEquals(result?.body, "hello world")
	}

	@Test
	fun testHelloService() {
		val result = testRestTemplate?.getForEntity("/hello-service", String::class.java)

		Assert.assertNotNull(result)
		Assert.assertEquals(result?.statusCode, HttpStatus.OK)
		Assert.assertEquals(result?.body, "hello service")
	}

	@Test
	fun testHelloDto() {
		val result = testRestTemplate?.getForEntity("/hello-dto", HelloDto::class.java)

		Assert.assertNotNull(result)
		Assert.assertEquals(result?.statusCode, HttpStatus.OK)
		Assert.assertEquals(result?.body, HelloDto("Hello from the dto"))
	}

}
