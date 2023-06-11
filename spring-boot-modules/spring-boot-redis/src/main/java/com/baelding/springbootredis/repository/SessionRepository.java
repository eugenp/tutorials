package com.baelding.springbootredis.repository;

import com.baelding.springbootredis.model.Session;
import org.springframework.data.repository.CrudRepository;

public interface SessionRepository extends CrudRepository<Session, String> {
}
