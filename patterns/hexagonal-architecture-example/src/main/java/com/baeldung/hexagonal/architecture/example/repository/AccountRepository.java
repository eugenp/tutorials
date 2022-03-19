package com.baeldung.hexagonal.architecture.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.baeldung.hexagonal.architecture.example.model.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

}