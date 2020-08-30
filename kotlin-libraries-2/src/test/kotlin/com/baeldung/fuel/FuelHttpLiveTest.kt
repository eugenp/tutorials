package com.baeldung.fuel

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.interceptors.cUrlLoggingRequestInterceptor
import com.github.kittinunf.fuel.gson.responseObject
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.rx.rx_object
import com.google.gson.Gson
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.io.File
import java.util.concurrent.CountDownLatch

/**
 * These live tests make connections to the external systems: http://httpbin.org, https://jsonplaceholder.typicode.com
 * Make sure these hosts are up and your internet connection is on before running the tests.
 */
internal class FuelHttpLiveTest {

    @Test
    fun whenMakingAsyncHttpGetRequest_thenResponseNotNullAndErrorNullAndStatusCode200() {

        val latch = CountDownLatch(1)

        "http://httpbin.org/get".httpGet().response{
            request, response, result ->

            val (data, error) = result

            Assertions.assertNull(error)
            Assertions.assertNotNull(data)
            Assertions.assertEquals(200,response.statusCode)

            latch.countDown()
        }

        latch.await()

    }

    @Test
    fun whenMakingSyncHttpGetRequest_thenResponseNotNullAndErrorNullAndStatusCode200() {

        val (request, response, result) = "http://httpbin.org/get".httpGet().response()
        val (data, error) = result

        Assertions.assertNull(error)
        Assertions.assertNotNull(data)
        Assertions.assertEquals(200,response.statusCode)

    }

    @Test
    fun whenMakingSyncHttpGetURLEncodedRequest_thenResponseNotNullAndErrorNullAndStatusCode200() {

        val (request, response, result) =
          "https://jsonplaceholder.typicode.com/posts"
          .httpGet(listOf("id" to "1")).response()
        val (data, error) = result

        Assertions.assertNull(error)
        Assertions.assertNotNull(data)
        Assertions.assertEquals(200,response.statusCode)

    }

    @Test
    fun whenMakingAsyncHttpPostRequest_thenResponseNotNullAndErrorNullAndStatusCode200() {

        val latch = CountDownLatch(1)

        Fuel.post("http://httpbin.org/post").response{
            request, response, result ->

            val (data, error) = result

            Assertions.assertNull(error)
            Assertions.assertNotNull(data)
            Assertions.assertEquals(200,response.statusCode)

            latch.countDown()
        }

        latch.await()

    }

    @Test
    fun whenMakingSyncHttpPostRequest_thenResponseNotNullAndErrorNullAndStatusCode200() {

        val (request, response, result) = Fuel.post("http://httpbin.org/post").response()
        val (data, error) = result

        Assertions.assertNull(error)
        Assertions.assertNotNull(data)
        Assertions.assertEquals(200,response.statusCode)
    }

    @Test
    fun whenMakingSyncHttpPostRequestwithBody_thenResponseNotNullAndErrorNullAndStatusCode200() {

        val (request, response, result) = Fuel.post("https://jsonplaceholder.typicode.com/posts")
          .body("{ \"title\" : \"foo\",\"body\" : \"bar\",\"id\" : \"1\"}")
          .response()

        val (data, error) = result

        Assertions.assertNull(error)
        Assertions.assertNotNull(data)
        Assertions.assertEquals(201,response.statusCode)
    }

    @Test
    fun givenFuelInstance_whenMakingSyncHttpGetRequest_thenResponseNotNullAndErrorNullAndStatusCode200() {

        FuelManager.instance.basePath = "http://httpbin.org"
        FuelManager.instance.baseHeaders = mapOf("OS" to "macOS High Sierra")

        FuelManager.instance.addRequestInterceptor(cUrlLoggingRequestInterceptor())
        FuelManager.instance.addRequestInterceptor(tokenInterceptor())


        val (request, response, result) = "/get"
          .httpGet().response()
        val (data, error) = result

        Assertions.assertNull(error)
        Assertions.assertNotNull(data)
        Assertions.assertEquals(200,response.statusCode)
    }

    @Test
    fun givenInterceptors_whenMakingSyncHttpGetRequest_thenResponseNotNullAndErrorNullAndStatusCode200() {

        FuelManager.instance.basePath = "http://httpbin.org"
        FuelManager.instance.addRequestInterceptor(cUrlLoggingRequestInterceptor())
        FuelManager.instance.addRequestInterceptor(tokenInterceptor())

        val (request, response, result) = "/get"
                .httpGet().response()
        val (data, error) = result

        Assertions.assertNull(error)
        Assertions.assertNotNull(data)
        Assertions.assertEquals(200,response.statusCode)
    }

    @Test
    fun whenDownloadFile_thenCreateFileResponseNotNullAndErrorNullAndStatusCode200() {

        Fuel.download("http://httpbin.org/bytes/32768").destination { response, url ->
            File.createTempFile("temp", ".tmp")
        }.response{
            request, response, result ->

            val (data, error) = result
            Assertions.assertNull(error)
            Assertions.assertNotNull(data)
            Assertions.assertEquals(200,response.statusCode)
        }
    }

    @Test
    fun whenDownloadFilewithProgressHandler_thenCreateFileResponseNotNullAndErrorNullAndStatusCode200() {

        val (request, response, result) = Fuel.download("http://httpbin.org/bytes/327680")
          .destination { response, url -> File.createTempFile("temp", ".tmp")
        }.progress { readBytes, totalBytes ->
            val progress = readBytes.toFloat() / totalBytes.toFloat()
        }.response ()

        val (data, error) = result
        Assertions.assertNull(error)
        Assertions.assertNotNull(data)
        Assertions.assertEquals(200,response.statusCode)


    }

    @Test
    fun whenMakeGetRequest_thenDeserializePostwithGson() {

        val latch = CountDownLatch(1)

        "https://jsonplaceholder.typicode.com/posts/1".httpGet().responseObject<Post> { _,_, result ->
            val post = result.component1()
            Assertions.assertEquals(1, post?.userId)
            latch.countDown()
        }

        latch.await()

    }

    @Test
    fun whenMakePOSTRequest_thenSerializePostwithGson() {

        val post = Post(1,1, "Lorem", "Lorem Ipse dolor sit amet")

        val (request, response, result) = Fuel.post("https://jsonplaceholder.typicode.com/posts")
                .header("Content-Type" to "application/json")
                .body(Gson().toJson(post).toString())
                .response()

        Assertions.assertEquals(201,response.statusCode)

    }

    @Test
    fun whenMakeGETRequestWithRxJava_thenDeserializePostwithGson() {

        val latch = CountDownLatch(1)


        "https://jsonplaceholder.typicode.com/posts?id=1"
                .httpGet().rx_object(Post.Deserializer()).subscribe{
                    res, throwable ->

                    val post = res.component1()
                    Assertions.assertEquals(1, post?.get(0)?.userId)
                    latch.countDown()
                }

        latch.await()

    }


// The new 1.3 coroutine APIs, aren't implemented yet in Fuel Library
//    @Test
//    fun whenMakeGETRequestUsingCoroutines_thenResponseStatusCode200() = runBlocking {
//            val (request, response, result) = Fuel.get("http://httpbin.org/get").awaitStringResponse()
//
//            result.fold({ data ->
//                Assertions.assertEquals(200, response.statusCode)
//
//            }, { error -> })
//    }

// The new 1.3 coroutine APIs, aren't implemented yet in Fuel Library
//    @Test
//    fun whenMakeGETRequestUsingCoroutines_thenDeserializeResponse() = runBlocking {
//            Fuel.get("https://jsonplaceholder.typicode.com/posts?id=1").awaitObjectResult(Post.Deserializer())
//                    .fold({ data ->
//                        Assertions.assertEquals(1, data.get(0).userId)
//                    }, { error -> })
//        }

    @Test
    fun whenMakeGETPostRequestUsingRoutingAPI_thenDeserializeResponse() {

        val latch = CountDownLatch(1)

        Fuel.request(PostRoutingAPI.posts("1",null))
          .responseObject(Post.Deserializer()) {
              request, response, result ->
              Assertions.assertEquals(1, result.component1()?.get(0)?.userId)
              latch.countDown()
          }

        latch.await()
    }

    @Test
    fun whenMakeGETCommentRequestUsingRoutingAPI_thenResponseStausCode200() {

        val latch = CountDownLatch(1)

        Fuel.request(PostRoutingAPI.comments("1",null))
          .responseString { request, response, result ->
            Assertions.assertEquals(200, response.statusCode)
            latch.countDown()
          }

        latch.await()
    }


}