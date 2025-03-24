
package com.baeldung.rest;

import com.sun.net.httpserver.HttpExchange;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.*;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RestApiServerTest {
	private RestApiServer restApiServer;
	private HttpExchange exchange;
	private ByteArrayOutputStream responseStream;

	@BeforeEach
	void setUp() {
		restApiServer = new RestApiServer();
		exchange = mock(HttpExchange.class);
		responseStream = new ByteArrayOutputStream();
	}

	private void mockRequest(String method, String body) throws IOException {
		InputStream requestBody = new ByteArrayInputStream(body.getBytes(StandardCharsets.UTF_8));
		when(exchange.getRequestMethod()).thenReturn(method);
		when(exchange.getRequestBody()).thenReturn(requestBody);
		when(exchange.getResponseBody()).thenReturn(responseStream);
		when(exchange.getResponseHeaders()).thenReturn(Mockito.mock(com.sun.net.httpserver.Headers.class));
	}

	@Test
	void testHandleGet() throws IOException {
		mockRequest("GET", "");
		restApiServer.handle(exchange);
		String response = responseStream.toString(StandardCharsets.UTF_8);
		assertEquals("[]", response.trim()); // Initial user list should be empty
	}

	@Test
	void testHandlePost() throws IOException {
		mockRequest("POST", "JohnDoe");
		restApiServer.handle(exchange);
		String response = responseStream.toString(StandardCharsets.UTF_8);
		assertTrue(response.contains("User added: JohnDoe"));
	}

	@Test
	void testHandlePostWithEmptyBody() throws IOException {
		mockRequest("POST", "");
		restApiServer.handle(exchange);
		String response = responseStream.toString(StandardCharsets.UTF_8);
		assertTrue(response.contains("Invalid user data"));
	}

	@Test
	void testHandlePutValidIndex() throws IOException {
		// First add a user
		mockRequest("POST", "JohnDoe");
		restApiServer.handle(exchange);

		mockRequest("PUT", "0:JaneDoe");
		restApiServer.handle(exchange);
		String response = responseStream.toString(StandardCharsets.UTF_8);
		assertTrue(response.contains("User updated: JaneDoe"));
	}

	@Test
	void testHandlePutInvalidIndex() throws IOException {
		mockRequest("PUT", "10:NewUser");
		restApiServer.handle(exchange);
		String response = responseStream.toString(StandardCharsets.UTF_8);
		assertTrue(response.contains("User not found"));
	}

	@Test
	void testHandleDeleteValidIndex() throws IOException {
		mockRequest("POST", "JohnDoe");
		restApiServer.handle(exchange);

		mockRequest("DELETE", "0");
		restApiServer.handle(exchange);
		String response = responseStream.toString(StandardCharsets.UTF_8);
		assertTrue(response.contains("User deleted: JohnDoe"));
	}

	@Test
	void testHandleDeleteInvalidIndex() throws IOException {
		mockRequest("DELETE", "5");
		restApiServer.handle(exchange);
		String response = responseStream.toString(StandardCharsets.UTF_8);
		assertTrue(response.contains("User not found"));
	}
}
