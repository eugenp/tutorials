package com.baeldung.restclient;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.support.HttpRequestWrapper;
 

class InterceptingClientHttpRequestTest {

    @Test
    void updateRequestAttribute() throws Exception {
        final String attrName = "attr1";
	final String attrValue = "value1";

	assertDoesNotThrow(() -> { 
	  ClientHttpRequestInterceptor interceptor = (request, body, execution) -> {
	  request.getAttributes().put(attrName, attrValue);
	  return execution.execute(request, body);
	  };
	});			    
    }
}
