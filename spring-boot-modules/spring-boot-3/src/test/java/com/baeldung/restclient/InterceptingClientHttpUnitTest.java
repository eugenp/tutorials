package com.baeldung.restclient;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;
import org.springframework.http.client.ClientHttpRequestInterceptor;

class InterceptingClientHttpUnitTest {

    @Test
    void updateRequestAttribute() throws Exception {
        String attrName = "attr1";
	String attrValue = "value1";

	assertDoesNotThrow(() -> { 
	  ClientHttpRequestInterceptor interceptor = (request, body, execution) -> {
	  request.getAttributes().put(attrName, attrValue);
	  return execution.execute(request, body);
	  };
	});			    
    }
}
