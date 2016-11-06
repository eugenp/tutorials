package com.baeldung.mdc.log4j;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.baeldung.mdc.DelegateBusiness;

final class Log4JBusinessService extends DelegateBusiness {
	
	private static final Logger logger = LogManager.getLogger();

	@Override
	protected void preTransfer(long amount) {
		logger.info("Preparing to transfer {}$.");
	}

	@Override
	protected void postTransfer(long amount, boolean outcome) {
		logger.info("Has transfer of {}$ completed successfully ? {}.");
	}
	
}