package com.baeldung.loadtesting.repository;

import com.baeldung.loadtesting.model.CustomerRewardsAccount;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class CustomerRewardsRepository {

  private AtomicInteger accountIds = new AtomicInteger();
	private final Map<Integer, CustomerRewardsAccount> accountsByCustomerId = new ConcurrentHashMap<>();

    public Optional<CustomerRewardsAccount> findByCustomerId(Integer customerId) {
    	return Optional.ofNullable(accountsByCustomerId.get(customerId));
    }

  public CustomerRewardsAccount save(CustomerRewardsAccount account) {
    if (account.getId() == null) {
      account.setId(accountIds.getAndIncrement());
    }
    if (account.getCustomerId() != null) {
      accountsByCustomerId.put(account.getCustomerId(), account);
    }
    return account;
  }
}
