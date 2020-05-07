package com.baeldung.hexagonal.banking.input.port;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

public class TransferMoneyCommand extends SelfValidating<TransferMoneyCommand>{
    
  
        @NotNull
        private Long sourceAccountNumber;

        @NotNull
        private Long targetAccountNumber;

        @NotNull
        private BigDecimal amount;
        
        @NotNull
        private int attemptedPin;

        public TransferMoneyCommand(
                        Long sourceAccountId,
                        Long targetAccountId,
                        BigDecimal amount,
                        int attemptedPin) {
                this.sourceAccountNumber = sourceAccountId;
                this.targetAccountNumber = targetAccountId;
                this.amount = amount;
                this.attemptedPin = attemptedPin;
                this.validateSelf();
        }

        public Long getSourceAccountNumber() {
            return sourceAccountNumber;
        }

        public Long getTargetAccountNumber() {
            return targetAccountNumber;
        }

        public BigDecimal getAmount() {
            return amount;
        }

        public int getAttemptedPin() {
            return attemptedPin;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((amount == null) ? 0 : amount.hashCode());
            result = prime * result + attemptedPin;
            result = prime * result + ((sourceAccountNumber == null) ? 0 : sourceAccountNumber.hashCode());
            result = prime * result + ((targetAccountNumber == null) ? 0 : targetAccountNumber.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            TransferMoneyCommand other = (TransferMoneyCommand) obj;
            if (amount == null) {
                if (other.amount != null)
                    return false;
            } else if (!amount.equals(other.amount))
                return false;
            if (attemptedPin != other.attemptedPin)
                return false;
            if (sourceAccountNumber == null) {
                if (other.sourceAccountNumber != null)
                    return false;
            } else if (!sourceAccountNumber.equals(other.sourceAccountNumber))
                return false;
            if (targetAccountNumber == null) {
                if (other.targetAccountNumber != null)
                    return false;
            } else if (!targetAccountNumber.equals(other.targetAccountNumber))
                return false;
            return true;
        }

        

}
