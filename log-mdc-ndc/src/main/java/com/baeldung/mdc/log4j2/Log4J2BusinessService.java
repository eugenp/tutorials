package com.baeldung.mdc.log4j2;

import org.apache.log4j.Logger;

import com.baeldung.mdc.BusinessService;

final class Log4J2BusinessService extends BusinessService {
	
	private Logger logger = Logger.getLogger(Log4J2BusinessService.class);

	@Override
	protected void beforeTransfer(long amount) {
		logger .info("Preparing to transfer " + amount + "$.");
	}

	@Override
	protected void afterTransfer(long amount, boolean outcome) {
		logger.info("Has transfer of " + amount + "$ completed successfully ? " + outcome + ".");
	}
	
}