package com.baeldung.mdc.log4j2;

import org.apache.log4j.Logger;

import com.baeldung.mdc.DelegateBusiness;

final class Log4J2BusinessService extends DelegateBusiness {
	
	private Logger logger = Logger.getLogger(Log4J2BusinessService.class);

	@Override
	protected void preTransfer(long amount) {
		logger .info("Preparing to transfer " + amount + "$.");
	}

	@Override
	protected void postTransfer(long amount, boolean outcome) {
		logger.info("Has transfer of " + amount + "$ completed successfully ? " + outcome + ".");
	}
	
}