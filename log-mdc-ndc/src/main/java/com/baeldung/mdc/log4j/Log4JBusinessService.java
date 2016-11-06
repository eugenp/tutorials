package com.baeldung.mdc.log4j;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.baeldung.mdc.BusinessService;

class Log4JBusinessService extends BusinessService {
	
	private static final Logger logger = LogManager.getLogger();

	@Override
	protected void beforeTransfer(long amount) {
		logger.info("Preparing to transfer {}$.");
	}

	@Override
	protected void afterTransfer(long amount, boolean outcome) {
		logger.info("Has transfer of {}$ completed successfully ? {}.");
	}
	
}