/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baeldung;

import java.io.IOException;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class ApplicationTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void testLocalErrorHandlingUsingOnErrorReturn() throws IOException {

        System.out.println("Testing local error handling using onErrorReturn");

        // Pass a username
        String i = webTestClient.get()
            .uri("/api/endpoint1?name={username}", "Tony")
            .accept(MediaType.TEXT_PLAIN)
            .exchange()
            .returnResult(String.class)
            .getResponseBody()
            .blockFirst();

        assertEquals("Hello, Tony", i);

        // Do not pass a username
        i = webTestClient.get()
            .uri("/api/endpoint1")
            .accept(MediaType.TEXT_PLAIN)
            .exchange()
            .returnResult(String.class)
            .getResponseBody()
            .blockFirst();

        assertEquals("Hello, Stranger", i);

    }

    @Test
    public void testLocalErrorHandlingUsingOnErrorResumeWithFallback() throws IOException {

        System.out.println("Testing local error handling using onErrorResume with fallback");

        // Pass a username
        String i = webTestClient.get()
            .uri("/api/endpoint2?name={username}", "Tony")
            .accept(MediaType.TEXT_PLAIN)
            .exchange()
            .returnResult(String.class)
            .getResponseBody()
            .blockFirst();

        assertEquals("Hello, Tony", i);

        // Do not pass a username
        i = webTestClient.get()
            .uri("/api/endpoint2")
            .accept(MediaType.TEXT_PLAIN)
            .exchange()
            .returnResult(String.class)
            .getResponseBody()
            .blockFirst();

        assertEquals("Hello, Stranger", i);

    }

    @Test
    public void testLocalErrorHandlingUsingOnErrorResumeWithDynamicFallbackValue() throws IOException {

        System.out.println("Testing local error handling using onErrorResume with dynamic fallback value");

        // Pass a username
        String i = webTestClient.get()
            .uri("/api/endpoint3?name={username}", "Tony")
            .accept(MediaType.TEXT_PLAIN)
            .exchange()
            .returnResult(String.class)
            .getResponseBody()
            .blockFirst();

        assertEquals("Hello, Tony", i);

        // Do not pass a username
        i = webTestClient.get()
            .uri("/api/endpoint3")
            .accept(MediaType.TEXT_PLAIN)
            .exchange()
            .returnResult(String.class)
            .getResponseBody()
            .blockFirst();

        assertEquals("Hi, I looked around for your name but found: No value present", i);

    }
    
    @Test
    public void testLocalErrorHandlingUsingOnErrorResumeWithCatchAndRethrow() throws IOException {

        System.out.println("Testing local error handling using onErrorResume with catch and rethrow");

        // Pass a username
        String i = webTestClient.get()
            .uri("/api/endpoint4?name={username}", "Tony")
            .accept(MediaType.TEXT_PLAIN)
            .exchange()
            .returnResult(String.class)
            .getResponseBody()
            .blockFirst();

        assertEquals("Hello, Tony", i);

        // Do not pass a username
        webTestClient.get()
            .uri("/api/endpoint4")
            .accept(MediaType.TEXT_PLAIN)
            .exchange()
            .expectStatus()
            .isBadRequest()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .expectBody()
            .jsonPath("$.message")
            .isNotEmpty()
            .jsonPath("$.message")
            .isEqualTo("please provide a name");

    }
        
    @Test
    public void testGlobalErrorHandlingUsingErrorWebExceptionHandler() throws IOException {

        System.out.println("Testing local error handling using ErrorWebExceptionHandler");

        // Pass a username
        String i = webTestClient.get()
            .uri("/api/endpoint5?name={username}", "Tony")
            .accept(MediaType.TEXT_PLAIN)
            .exchange()
            .returnResult(String.class)
            .getResponseBody()
            .blockFirst();

        assertEquals("Hello, Tony", i);

        // Do not pass a username
        webTestClient.get()
            .uri("/api/endpoint5")
            .accept(MediaType.TEXT_PLAIN)
            .exchange()
            .expectStatus()
            .isBadRequest()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .expectBody()
            .jsonPath("$.message")
            .isNotEmpty()
            .jsonPath("$.message")
            .isEqualTo("please provide a name");

    }
            
}
