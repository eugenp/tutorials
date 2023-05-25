package com.baeldung.spring.cloud.apigateway.fallback;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.test.context.junit4.SpringRunner;

import com.netflix.hystrix.exception.HystrixTimeoutException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WeatherServiceFallbackUnitTest {

    private static final String ROUTE = "weather-service";

    @Autowired
    private WeatherServiceFallback fallback;

    @Test
    public void testWhenGetRouteThenReturnWeatherServiceRoute() {
        assertEquals(ROUTE, fallback.getRoute());

    }

    @Test
    public void testFallbackResponse_whenHystrixException_thenGatewayTimeout() throws Exception {
        HystrixTimeoutException exception = new HystrixTimeoutException();
        ClientHttpResponse response = fallback.fallbackResponse(ROUTE, exception);

        assertEquals(HttpStatus.GATEWAY_TIMEOUT, response.getStatusCode());
    }

    @Test
    public void testFallbackResponse_whenNonHystrixException_thenInternalServerError() throws Exception {
        RuntimeException exception = new RuntimeException("Test exception");
        ClientHttpResponse response = fallback.fallbackResponse(ROUTE, exception);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

}
