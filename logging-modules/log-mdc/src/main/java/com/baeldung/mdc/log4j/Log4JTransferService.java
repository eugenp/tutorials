package com.baeldung.mdc.log4j;

import org.apache.log4j.Logger;

import com.baeldung.mdc.TransferService;

public class Log4JTransferService extends TransferService {

    private Logger logger = Logger.getLogger(Log4JTransferService.class);

    @Override
    protected void beforeTransfer(long amount) {
        logger.info("Preparing to transfer " + amount + "$.");
    }

    @Override
    protected void afterTransfer(long amount, boolean outcome) {
        logger.info("Has transfer of " + amount + "$ completed successfully ? " + outcome + ".");
    }

}