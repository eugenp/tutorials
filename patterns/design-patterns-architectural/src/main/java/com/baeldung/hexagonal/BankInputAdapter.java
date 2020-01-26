package com.baeldung.hexagonal;

public class BankInputAdapter implements IBankInputPort {

        private BankDomainService domainService;

        public BankInputAdapter(BankDomainService domainService) {
                this.domainService = domainService;
        }

        @Override public void credit(String accountNumber, Double amount) {
                domainService.updateBalance(accountNumber, amount);
        }

        @Override public void debit(String accountNumber, Double amount) {
                if (amount > 0) {
                        amount = amount * -1.0;
                }
                domainService.updateBalance(accountNumber, amount);
        }
}
