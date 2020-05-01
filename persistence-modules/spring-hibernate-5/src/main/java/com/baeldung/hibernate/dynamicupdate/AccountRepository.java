package com.baeldung.hibernate.dynamicupdate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.baeldung.hibernate.dynamicupdate.model.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

}
