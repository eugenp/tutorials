package com.baeldung.restclient;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.InterceptingClientHttpRequestFactory;
import org.springframework.http.client.support.HttpRequestWrapper;
import org.springframework.mock.http.client.MockClientHttpRequest;
import org.springframework.mock.http.client.MockClientHttpResponse;

import static org.assertj.core.api.Assertions.assertThat;

class InterceptingClientHttpRequestTest {

	private RequestFactoryMock requestFactoryMock = new RequestFactoryMock();

	private MockClientHttpRequest requestMock = new MockClientHttpRequest();

	private MockClientHttpResponse responseMock = new MockClientHttpResponse();

	private InterceptingClientHttpRequestFactory requestFactory;

	@BeforeEach
	void beforeEach() {
		this.requestMock.setResponse(this.responseMock);
	}

	@Test
	void updateRequestAttribute() throws Exception {
		final String attrName = "attr1";
		final String attrValue = "value1";

		ClientHttpRequestInterceptor interceptor = (request, body, execution) -> {
			request.getAttributes().put(attrName, attrValue);
			return execution.execute(request, body);
		};
	}

}
