package com.ticketapp;

import java.util.Arrays;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class TestContext {

	public static final String BASE_URL = "http://localhost:8080";

	public static String path(String path) {
		return BASE_URL + path;
	}

	public static HttpHeaders getHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		return headers;
	}

}
