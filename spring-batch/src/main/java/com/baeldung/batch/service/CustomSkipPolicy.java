package com.baeldung.batch.service;

import org.springframework.batch.core.step.skip.SkipLimitExceededException;
import org.springframework.batch.core.step.skip.SkipPolicy;

public class CustomSkipPolicy implements SkipPolicy {

    private static final int MAX_SKIP_COUNT = 2;
    private static final int INVALID_TX_AMOUNT_LIMIT = -1000;

    @Override
    public boolean shouldSkip(Throwable throwable, long skipCount) throws SkipLimitExceededException {
        if (throwable instanceof MissingUsernameException && skipCount < MAX_SKIP_COUNT) {
            return true;
        }

        if (throwable instanceof NegativeAmountException ex && skipCount < MAX_SKIP_COUNT ) {
            return ex.getAmount() >= INVALID_TX_AMOUNT_LIMIT;
        }

        return false;
    }
}
