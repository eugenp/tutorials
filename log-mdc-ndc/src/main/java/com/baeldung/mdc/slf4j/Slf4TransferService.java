package com.baeldung.mdc.slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baeldung.mdc.TransferService;

final class Slf4TransferService extends TransferService {

    private static final Logger logger = LoggerFactory.getLogger(Slf4TransferService.class);

    @Override
    protected void beforeTransfer(long amount) {
        logger.info("Preparing to transfer {}$.", amount);
    }

    @Override
    protected void afterTransfer(long amount, boolean outcome) {
        logger.info("Has transfer of {}$ completed successfully ? {}.", amount, outcome);
    }

}