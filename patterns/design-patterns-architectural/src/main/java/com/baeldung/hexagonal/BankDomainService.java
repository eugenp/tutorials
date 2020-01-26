package com.baeldung.hexagonal;

public class BankDomainService {

        private IBankOutputPort outputPort;

        public BankDomainService(IBankOutputPort outputPort) {
                this.outputPort = outputPort;
        }

        public void updateBalance(String accountNumber, Double amount) {
                Double balance = outputPort.getBalance(accountNumber);

                Double newBalance = balance + amount;
                if (newBalance > 0) {
                        outputPort.updateBalance(accountNumber, newBalance);
                }
        }

}
