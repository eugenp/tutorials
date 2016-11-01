package com.baeldung.mdc.log4j;

import org.apache.log4j.Logger;

import com.baeldung.mdc.BusinessService;

public class Log4jBusinessService implements BusinessService {
	
	private final static Logger logger = Logger.getLogger(Log4jBusinessService.class);
	
	@Override
	public boolean transfer(Long amount) {
		logger.info("Executing transaction #" + amount );
		return false;
	}
	
}