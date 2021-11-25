package com.baeldung.adapters.persistence;


import com.baeldung.adapters.persistence.entity.GamerAccountData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringJpaGamerAccountRepository extends JpaRepository<GamerAccountData,Long> {

}
