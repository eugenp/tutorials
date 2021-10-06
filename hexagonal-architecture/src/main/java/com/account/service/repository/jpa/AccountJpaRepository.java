package com.account.service.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountJpaRepository extends JpaRepository<AccountEntity, Long> {
  Optional<AccountEntity> findByAccountId(final String accountId);
}
