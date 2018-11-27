package com.baeldung.spring.cloud.feign.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("spring-cloud-eureka-client")
public interface GreetingClient {
    @RequestMapping("/greeting")
    String greeting();
}
