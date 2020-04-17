package com.baeldung.awsappsync;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AwsAppSyncApplicationTests {

	static String apiUrl = "https://m4i3b6icrrb7livfbypfspiifi.appsync-api.us-east-2.amazonaws.com";
	static String apiKey = "da2-es2s6oj4mzhbxk5yu26ss2ruj4";
	static String API_KEY_HEADER = "x-api-key";

	static WebClient.RequestBodySpec requestBodySpec;

	@BeforeAll
	static void setUp() {
		requestBodySpec = WebClient
				.builder()
				.baseUrl(apiUrl)
				.defaultHeader(API_KEY_HEADER, apiKey)
				.build()
				.method(HttpMethod.POST)
				.uri("/graphql");
	}

	@Test
	void givenGraphQuery_whenListEvents_thenReturnAllEvents() {

		Map<String, Object> requestBody = new HashMap<>();
		requestBody.put("query", "query ListEvents {\n" +
				"  listEvents {\n" +
				"    items {\n" +
				"      id\n" +
				"      name\n" +
				"      where\n" +
				"      when\n" +
				"      description\n" +
				"    }\n" +
				"  }\n" +
				"}");
		requestBody.put("variables", "");
		requestBody.put("operationName", "ListEvents");

		WebClient.ResponseSpec response = requestBodySpec
				.body(BodyInserters.fromValue(requestBody))
				.accept(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML)
				.acceptCharset(StandardCharsets.UTF_8)
				.retrieve();

		String bodyString = response.bodyToMono(String.class).block();

		assertNotNull(bodyString);
		assertTrue(bodyString.contains("My First Event"));
		assertTrue(bodyString.contains("where"));
		assertTrue(bodyString.contains("when"));
	}

	@Test
	void givenGraphAdd_whenMutation_thenReturnIdNameDesc() {

		String queryString = "mutation add {\n" +
				"    createEvent(\n" +
				"        name:\"My added GraphQL event\"\n" +
				"        where:\"Day 2\"\n" +
				"        when:\"Saturday night\"\n" +
				"        description:\"Studying GraphQL\"\n" +
				"    ){\n" +
				"        id\n" +
				"        name\n" +
				"        description\n" +
				"    }\n" +
				"}";


		Map<String, Object> requestBody = new HashMap<>();
		requestBody.put("query", queryString);
		requestBody.put("variables", "");
		requestBody.put("operationName", "add");

		WebClient.ResponseSpec response = requestBodySpec
				.body(BodyInserters.fromValue(requestBody))
				.accept(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML)
				.acceptCharset(StandardCharsets.UTF_8)
				.retrieve();

		String bodyString = response.bodyToMono(String.class).block();

		assertNotNull(bodyString);
		assertTrue(bodyString.contains("My added GraphQL event"));
		assertFalse(bodyString.contains("where"));
		assertFalse(bodyString.contains("when"));
	}
}
