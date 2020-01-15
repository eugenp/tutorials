package org.nganga.bank.account.port;

import java.io.IOException;
import java.util.List;

import org.nganga.bank.account.adapter.CustomerAccountFileAdapter;
import org.nganga.bank.account.domain.Account;
import org.nganga.bank.account.domain.Customer;

public class CustomerAccountService {
	private CustomerAccountPort customerAccountPort;

	public CustomerAccountService() {
		customerAccountPort = new CustomerAccountFileAdapter();
	}

	public void createAccount(Customer c) {
		customerAccountPort.createAccount(c);

	}

	public List<Account> getCustomerAccounts(String customerIdentity) {

		return customerAccountPort.getCustomerAccounts(customerIdentity);
	}

}
