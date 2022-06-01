package com.baeldung.spring.cloud.greeting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * This class provides operations on the Validation service.
 *
 * <p>
 * When booting up, Spring will try and find a service named "Validation" (see
 * the FeignClient below) under the available ZooKeeper instance.
 * </p>
 *
 */
@Configuration
@EnableFeignClients
@EnableDiscoveryClient
public class HelloWorldClient {

	@Autowired
	private TheClient theClient;

	@FeignClient(name = "HelloWorld")
	interface TheClient {

		@RequestMapping(path = "/helloworld", method = RequestMethod.GET)
		@ResponseBody
		String HelloWorld();
	}

	public String HelloWorld() {
		return theClient.HelloWorld();
	}

}
