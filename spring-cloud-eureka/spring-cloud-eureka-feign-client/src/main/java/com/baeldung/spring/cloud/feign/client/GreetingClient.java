package com.baeldung.spring.cloud.feign.client;

import com.baeldung.spring.cloud.eureka.client.GreetingController;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient("spring-cloud-eureka-client")
public interface GreetingClient extends GreetingController {
}
