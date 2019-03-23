package com.baeldung.kotlin.khttp

import khttp.structures.files.FileLike
import org.json.JSONObject
import org.junit.Test
import java.beans.ExceptionListener
import java.beans.XMLEncoder
import java.io.*
import java.lang.Exception
import java.net.ConnectException
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.fail

class KhttpLiveTest {

    @Test
    fun whenHttpGetRequestIsMade_thenArgsAreReturned() {
        val response = khttp.get(
                url = "http://httpbin.org/get",
                params = mapOf("p1" to "1", "p2" to "2"))
        val args = response.jsonObject.getJSONObject("args")

        assertEquals("1", args["p1"])
        assertEquals("2", args["p2"])
    }

    @Test
    fun whenAlternateHttpGetRequestIsMade_thenArgsAreReturned() {
        val response = khttp.request(
                method = "GET",
                url = "http://httpbin.org/get",
                params = mapOf("p1" to "1", "p2" to "2"))
        val args = response.jsonObject.getJSONObject("args")

        assertEquals("1", args["p1"])
        assertEquals("2", args["p2"])
    }

    @Test
    fun whenHeadersAreSet_thenHeadersAreSent() {
        val response = khttp.get(
                url = "http://httpbin.org/get",
                headers = mapOf("header1" to "1", "header2" to "2"))
        val headers = response.jsonObject.getJSONObject("headers")

        assertEquals("1", headers["Header1"])
        assertEquals("2", headers["Header2"])
    }

    @Test
    fun whenHttpPostRequestIsMadeWithJson_thenBodyIsReturned() {
        val response = khttp.post(
                url = "http://httpbin.org/post",
                params = mapOf("p1" to "1", "p2" to "2"),
                json = mapOf("pr1" to "1", "pr2" to "2"))

        val args = response.jsonObject.getJSONObject("args")

        assertEquals("1", args["p1"])
        assertEquals("2", args["p2"])

        val json = response.jsonObject.getJSONObject("json")

        assertEquals("1", json["pr1"])
        assertEquals("2", json["pr2"])
    }

    @Test
    fun whenHttpPostRequestIsMadeWithMapData_thenBodyIsReturned() {
        val response = khttp.post(
                url = "http://httpbin.org/post",
                params = mapOf("p1" to "1", "p2" to "2"),
                data = mapOf("pr1" to "1", "pr2" to "2"))

        val args = response.jsonObject.getJSONObject("args")

        assertEquals("1", args["p1"])
        assertEquals("2", args["p2"])

        val form = response.jsonObject.getJSONObject("form")

        assertEquals("1", form["pr1"])
        assertEquals("2", form["pr2"])
    }

    @Test
    fun whenHttpPostRequestIsMadeWithFiles_thenBodyIsReturned() {
        val response = khttp.post(
                url = "http://httpbin.org/post",
                params = mapOf("p1" to "1", "p2" to "2"),
                files = listOf(
                        FileLike("file1", "content1"),
                        FileLike("file2", javaClass.getResource("KhttpTest.class").openStream().readBytes())))

        val args = response.jsonObject.getJSONObject("args")

        assertEquals("1", args["p1"])
        assertEquals("2", args["p2"])

        val files = response.jsonObject.getJSONObject("files")

        assertEquals("content1", files["file1"])
    }

    @Test
    fun whenHttpPostRequestIsMadeWithInputStream_thenBodyIsReturned() {
        val response = khttp.post(
                url = "http://httpbin.org/post",
                params = mapOf("p1" to "1", "p2" to "2"),
                data = ByteArrayInputStream("content!".toByteArray()))

        val args = response.jsonObject.getJSONObject("args")

        assertEquals("1", args["p1"])
        assertEquals("2", args["p2"])

        assertEquals("content!", response.jsonObject["data"])
    }

    @Test
    fun whenHttpPostStreamingRequestIsMade_thenBodyIsReturnedInChunks() {
        val response = khttp.post(
                url = "http://httpbin.org/post",
                stream = true,
                json = mapOf("pr1" to "1", "pr2" to "2"))

        val baos = ByteArrayOutputStream()
        response.contentIterator(chunkSize = 10).forEach { arr : ByteArray -> baos.write(arr) }
        val json = JSONObject(String(baos.toByteArray())).getJSONObject("json")

        assertEquals("1", json["pr1"])
        assertEquals("2", json["pr2"])
    }

    @Test
    fun whenHttpRequestFails_thenExceptionIsThrown() {
        try {
            khttp.get(url = "http://localhost/nothing/to/see/here")

            fail("Should have thrown an exception")
        } catch (e : ConnectException) {
            //Ok
        }
    }

    @Test
    fun whenHttpNotFound_thenExceptionIsThrown() {
        val response = khttp.get(url = "http://httpbin.org/nothing/to/see/here")

        assertEquals(404, response.statusCode)
    }
}