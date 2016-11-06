package com.baeldung.mdc.slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baeldung.mdc.DelegateBusiness;

final class Slf4jBusinessService extends DelegateBusiness {
	
	private static final Logger logger = LoggerFactory.getLogger(Slf4jBusinessService.class);

	@Override
	protected void preTransfer(long amount) {
		logger.info("Preparing to transfer " + amount + "$.");
	}

	@Override
	protected void postTransfer(long amount, boolean outcome) {
		logger.info("Has transfer of " + amount + "$ completed successfully ? " + outcome + ".");
	}
	
}