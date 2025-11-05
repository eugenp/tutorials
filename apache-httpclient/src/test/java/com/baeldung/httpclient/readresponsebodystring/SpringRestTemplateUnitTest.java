package com.baeldung.httpclient.readresponsebodystring;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

public class SpringRestTemplateUnitTest {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    public static final String DUMMY_URL = "https://postman-echo.com/get";

    @Test
    public void whenUseRestTemplate_thenCorrect() {
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(DUMMY_URL, String.class);
        logger.debug(response);
    }

}
