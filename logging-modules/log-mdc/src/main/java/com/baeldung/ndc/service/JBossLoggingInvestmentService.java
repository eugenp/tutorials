package com.baeldung.ndc.service;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("JBossLoggingInvestmentService")
public class JBossLoggingInvestmentService implements InvestmentService {
    private static final Logger logger = Logger.getLogger(JBossLoggingInvestmentService.class);

    @Override
    public void beforeTransfer(long amount) {
        logger.infov("Preparing to transfer {0}$.", amount);
    }

    @Override
    public void afterTransfer(long amount, boolean outcome) {
        logger.infov("Has transfer of {0}$ completed successfully ? {1}.", amount, outcome);
    }
}