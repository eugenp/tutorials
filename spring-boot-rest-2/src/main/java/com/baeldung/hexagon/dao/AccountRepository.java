package com.baeldung.hexagon.dao;

import com.baeldung.hexagon.domain.Account;

public interface AccountRepository {

	public Account findAccount(String acctNumber);

}
