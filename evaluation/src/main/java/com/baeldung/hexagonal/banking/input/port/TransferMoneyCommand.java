package com.baeldung.hexagonal.banking.input.port;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

public class TransferMoneyCommand extends SelfValidating<TransferMoneyCommand>{
    
  
        @NotNull
        private final Long sourceAccountId;

        @NotNull
        private final Long targetAccountId;

        @NotNull
        private final BigDecimal amount;
        
        @NotNull
        private final int attemptedPin;

        public TransferMoneyCommand(
                        Long sourceAccountId,
                        Long targetAccountId,
                        BigDecimal amount,
                        int attemptedPin) {
                this.sourceAccountId = sourceAccountId;
                this.targetAccountId = targetAccountId;
                this.amount = amount;
                this.attemptedPin = attemptedPin;
                this.validateSelf();
        }

        public Long getSourceAccountId() {
            return sourceAccountId;
        }

        public Long getTargetAccountId() {
            return targetAccountId;
        }

        public BigDecimal getAmount() {
            return amount;
        }

        public int getAttemptedPin() {
            return attemptedPin;
        }


}
