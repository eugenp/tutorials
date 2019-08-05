package com.baeldung.patterns.hexagonal.transfer;

import lombok.Builder;
import lombok.Getter;

public interface TransferFundsUseCase {

    void transferFunds(TransferFundsCommand command);

    @Getter
    @Builder
    class TransferFundsCommand {
        private final String from;
        private final String to;
        private final int amount;

        // Constructor and getters omitted
    }

}
