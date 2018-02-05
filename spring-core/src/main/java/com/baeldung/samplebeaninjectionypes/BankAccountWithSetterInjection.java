package com.baeldung.samplebeaninjectionypes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class BankAccountWithSetterInjection {

        private BankAccountService bankAccountService;

        public BankAccountWithSetterInjection(BankAccountService service) {
                this.bankAccountService = service;
        }

        @Autowired
        public void setBankAccountService(BankAccountService bankAccountService) {
                this.bankAccountService = bankAccountService;
        }

        public AccountDetails openAccount(Long accountNumber, String accountType, String owner) {
                return bankAccountService.openAccount(accountNumber, accountType, owner);
        }
}
