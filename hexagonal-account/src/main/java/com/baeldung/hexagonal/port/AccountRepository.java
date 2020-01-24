/**
 * 
 */
package com.baeldung.hexagonal.port;

import java.util.List;

import com.baeldung.hexagonal.core.domain.Account;

/**
 * @author Dinesh.Rajput
 *
 */
public interface AccountRepository {
	
	void createAccount(Account account);
	
	Account getAccount(Long accountNumber);
	
	List<Account> allAccounts();
}
