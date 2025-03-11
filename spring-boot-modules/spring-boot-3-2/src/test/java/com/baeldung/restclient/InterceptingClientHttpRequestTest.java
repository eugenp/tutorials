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
		requestMock = new MockClientHttpRequest() {
			@Override
			protected ClientHttpResponse executeInternal() {
				assertThat(getAttributes()).containsEntry(attrName, attrValue);
				return responseMock;
			}
		};
		requestFactory = new InterceptingClientHttpRequestFactory(requestFactoryMock, Collections.singletonList(interceptor));

		ClientHttpRequest request = requestFactory.createRequest(URI.create("https://example.com"), HttpMethod.GET);
		request.execute();
	}

	private class RequestFactoryMock implements ClientHttpRequestFactory {

		@Override
		public ClientHttpRequest createRequest(URI uri, HttpMethod httpMethod) throws IOException {
			requestMock.setURI(uri);
			requestMock.setMethod(httpMethod);
			return requestMock;
		}

	}

}
