package com.baeldung.ndc.log4j;

import org.apache.log4j.Logger;

import com.baeldung.ndc.InvestmentService;

public class Log4jInvestmentService extends InvestmentService {
    private Logger logger = Logger.getLogger(Log4jInvestmentService.class);

    @Override
    protected void beforeTransfer(long amount) {
        logger.info("Preparing to transfer " + amount + "$.");
    }

    @Override
    protected void afterTransfer(long amount, boolean outcome) {
        logger.info("Has transfer of " + amount + "$ completed successfully ? " + outcome + ".");
    }
}