package com.ticketapp.core.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ticketapp.core.application.domain.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {

}
