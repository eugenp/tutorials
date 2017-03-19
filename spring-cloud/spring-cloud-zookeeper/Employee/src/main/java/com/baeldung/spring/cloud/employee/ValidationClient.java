package com.baeldung.spring.cloud.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
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
public class ValidationClient {

	@Autowired
	private TheClient theClient;

	@FeignClient(name = "Validation")
	interface TheClient {

		@RequestMapping(path = "/validation/isvalid", method = RequestMethod.POST, consumes = MediaType.TEXT_PLAIN_VALUE)
		@ResponseBody
		Boolean isValid(@RequestBody String email);
	}

	/**
	 * Initiate call to Validation.
	 *
	 * @param email
	 * @return the response
	 */
	public Boolean isValid(String email) {
		return theClient.isValid(email);
	}

}
