package org.nganga.bank.account.port;

import java.io.IOException;
import java.util.List;

import org.nganga.bank.account.domain.Account;
import org.nganga.bank.account.domain.Customer;

public interface CustomerAccountPort {
	public void createAccount(Customer c);

	public List<Account> getCustomerAccounts(String customerIdentity);

}
