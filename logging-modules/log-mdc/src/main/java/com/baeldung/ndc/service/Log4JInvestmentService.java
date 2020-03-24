package com.baeldung.ndc.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("log4jInvestmentService")
public class Log4JInvestmentService implements InvestmentService {
    private Logger logger = Logger.getLogger(Log4JInvestmentService.class);

    @Override
    public void beforeTransfer(long amount) {
        logger.info("Preparing to transfer " + amount + "$.");
    }

    @Override
    public void afterTransfer(long amount, boolean outcome) {
        logger.info("Has transfer of " + amount + "$ completed successfully ? " + outcome + ".");
    }
}