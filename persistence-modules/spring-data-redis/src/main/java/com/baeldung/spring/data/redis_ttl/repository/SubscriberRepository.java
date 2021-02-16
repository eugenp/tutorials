package com.baeldung.spring.data.redis_ttl.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.baeldung.spring.data.redis_ttl.entity.Subscriber;

@Repository
public interface SubscriberRepository extends CrudRepository<Subscriber, Long>{
 
}