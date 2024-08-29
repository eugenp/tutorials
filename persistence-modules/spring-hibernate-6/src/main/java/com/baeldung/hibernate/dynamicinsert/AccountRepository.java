package com.baeldung.hibernate.dynamicinsert;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.baeldung.hibernate.dynamicinsert.model.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

}
