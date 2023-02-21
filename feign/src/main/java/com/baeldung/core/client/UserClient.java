package com.baeldung.core.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.baeldung.core.config.FeignConfig;

@FeignClient(name = "user-client", url="https://jsonplaceholder.typicode.com", configuration = FeignConfig.class)
public interface UserClient {

	@GetMapping(value = "/users")
	String getUsers();
}