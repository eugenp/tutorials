package com.baeldung.beans.service;

import com.baeldung.beans.model.Info;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.support.RestGatewaySupport;

@Slf4j
@Service
public class MyServiceImpl  extends RestGatewaySupport implements MyService {

    private String endpoint;

    private RestTemplate restTemplate;

    public MyServiceImpl(
            @Value("${pcf.v1.endpoint}") final String endpoint,
            RestTemplate restTemplate) {
        this.endpoint = endpoint;
        this.restTemplate = restTemplate;

        setRestTemplate(restTemplate);
        LOGGER.trace("Service loaded with endpoint: {}", endpoint);
    }

    @Override
    public Info getVersion() {
        final Info info = getRestTemplate().getForObject(endpoint, Info.class);
        LOGGER.info(info.toString());
        return info;
    }
}
