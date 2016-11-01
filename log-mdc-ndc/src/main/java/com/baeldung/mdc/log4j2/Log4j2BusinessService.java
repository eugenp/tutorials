package com.baeldung.mdc.log4j2;



import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.baeldung.mdc.BusinessService;

public class Log4j2BusinessService implements BusinessService {
	
	private static final Logger logger = LogManager.getLogger(); //Log4j2BusinessService.class);
	
	@Override
	public boolean transfer(Long amount) {
		logger.info("Executing transaction #{}", amount );
		return false;
	}
	
}