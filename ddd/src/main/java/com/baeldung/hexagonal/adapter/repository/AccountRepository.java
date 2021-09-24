package com.baeldung.hexagonal.adapter.repository;

import com.baeldung.hexagonal.adapter.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {
}
