package com.baeldung.beans.controller;

import com.baeldung.beans.model.CFVersion;
import com.baeldung.beans.model.Info;
import com.baeldung.beans.service.MyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@Slf4j
@RestController
@RequestMapping(value = "/api", produces = APPLICATION_JSON_UTF8_VALUE)
public class MyEndpoint {

	private MyService myService;

	public MyEndpoint(MyService myService) {
		this.myService = myService;
	}

	@GetMapping(value = "/info")
	public CFVersion getInfo() {
		final Info info = myService.getVersion();
		LOGGER.trace("Output: {}", info);
		return new CFVersion(info.getApi_version());
	}

}