package com.baeldung.spring.cloud.vaultsample;

import com.baeldung.spring.cloud.vaultsample.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepo extends JpaRepository<Account, Long>{

}
