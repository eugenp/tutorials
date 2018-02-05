package com.baeldung.samplebeaninjectionypes;

public class BankAccountService {

        public AccountDetails openAccount(Long accountNumber, String accountType, String owner) {
                AccountDetails accountDetails = new AccountDetails(accountNumber,accountType,owner);
                return accountDetails;
        }
}
