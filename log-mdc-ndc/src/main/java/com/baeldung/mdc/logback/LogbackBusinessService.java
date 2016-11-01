package com.baeldung.mdc.logback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baeldung.mdc.BusinessService;

public class LogbackBusinessService implements BusinessService {

	private static final Logger logger = LoggerFactory.getLogger(LogbackBusinessService.class);

	@Override
	public boolean transfer(Long amount) {
		logger.info("Executing transaction #{}", amount);
		return false;
	}

}