package com.baeldung.ndc.log4j2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.baeldung.ndc.InvestmentService;

public class Log4j2InvestmentService extends InvestmentService {
    private static final Logger logger = LogManager.getLogger();

    @Override
    protected void beforeTransfer(long amount) {
        logger.info("Preparing to transfer {}$.", amount);
    }

    @Override
    protected void afterTransfer(long amount, boolean outcome) {
        logger.info("Has transfer of {}$ completed successfully ? {}.", amount, outcome);
    }
}