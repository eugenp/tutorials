package com.baeldung.mdc.jboss;

import org.jboss.logging.Logger;

import com.baeldung.mdc.TransferService;

public class JBossLoggingTransferService extends TransferService {

    private static final Logger logger = Logger.getLogger(JBossLoggingTransferService.class);

    @Override
    protected void beforeTransfer(long amount) {
        logger.infov("Preparing to transfer {0}$.", amount);
    }

    @Override
    protected void afterTransfer(long amount, boolean outcome) {
        logger.infov("Has transfer of {0}$ completed successfully ? {1}.", amount, outcome);
    }

}