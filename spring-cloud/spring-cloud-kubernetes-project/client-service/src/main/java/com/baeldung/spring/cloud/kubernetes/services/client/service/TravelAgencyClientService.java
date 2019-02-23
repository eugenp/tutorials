package com.baeldung.spring.cloud.kubernetes.services.client.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TravelAgencyClientService {

    private final RestTemplate restTemplate;

    private static final Log log = LogFactory.getLog(TravelAgencyClientService.class);

    public static final String FIND_NEW_TRAVEL_DEALS = "find new travel deals";

    public TravelAgencyClientService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @HystrixCommand(fallbackMethod = "getFallbackName", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000")
    })
    public String requestDeals(String to,
                                 String from) {

        String url = String.format("%s/deals/%s",
                                   to,
                                   from);

        log.info("--- Requesting travel deals to travel agency " + url);

        return restTemplate.getForObject(url, String.class);
    }

    private String getFallbackName(String to,
                                   String from) {
        log.error("--- This travel agency  (" + to + ") not available now, please come back later (Fallback) client:" + from);
        return FIND_NEW_TRAVEL_DEALS;
    }
}
