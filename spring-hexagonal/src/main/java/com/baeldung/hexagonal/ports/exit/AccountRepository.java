package com.baeldung.hexagonal.ports.exit;

import com.baeldung.hexagonal.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findAccountsByBalanceGreaterThan(BigDecimal minBalance);
}
