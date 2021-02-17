package com.baeldung.spring.data.redis_ttl.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.baeldung.spring.data.redis_ttl.entity.Gatekeeper;

@Repository
public interface GatekeeperRepository extends CrudRepository<Gatekeeper, Long>{
 
}