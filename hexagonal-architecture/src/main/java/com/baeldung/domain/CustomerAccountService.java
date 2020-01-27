package com.baeldung.domain;

import java.util.List;

import com.baeldung.adapter.CustomerAccountFileAdapter;
import com.baeldung.domain.Account;
import com.baeldung.domain.Customer;
import com.baeldung.port.CustomerAccountPort;

public class CustomerAccountService {

    private CustomerAccountPort customerAccountPort;
    public CustomerAccountService() {
        customerAccountPort = new CustomerAccountFileAdapter();

    }

    public String createAccount(Customer c) throws BlacklistedException, InvalidSizeIdentityException {
        if (CustomerAccountChecker.isCustomerBlacklisted(c)) {
            throw new BlacklistedException("This user is not allowed to open an account with us");
        }
        if (CustomerAccountChecker.isIdentityValid(c)) {
            throw new InvalidSizeIdentityException("Invalid Id");
        }

        return customerAccountPort.createAccount(c);

    }

    public List<Account> getCustomerAccounts(String customerIdentity) {

        return customerAccountPort.getCustomerAccounts(customerIdentity);
    }

}
