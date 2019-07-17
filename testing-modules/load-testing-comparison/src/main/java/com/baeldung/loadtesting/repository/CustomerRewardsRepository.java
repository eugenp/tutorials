package com.baeldung.loadtesting.repository;

import com.baeldung.loadtesting.model.CustomerRewardsAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRewardsRepository extends JpaRepository<CustomerRewardsAccount, Integer> {

    Optional<CustomerRewardsAccount> findByCustomerId(Integer customerId);
}
