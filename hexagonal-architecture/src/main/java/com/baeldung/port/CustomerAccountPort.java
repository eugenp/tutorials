package com.baeldung.port;

import java.util.List;

import com.baeldung.domain.Account;
import com.baeldung.domain.Customer;

public interface CustomerAccountPort {

    public String createAccount(Customer c);

    public List<Account> getCustomerAccounts(String customerIdentity);

}
