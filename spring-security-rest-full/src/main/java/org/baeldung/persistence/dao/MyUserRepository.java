package org.baeldung.persistence.dao;

import org.baeldung.persistence.model.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

public interface MyUserRepository extends JpaRepository<MyUser, Long>, QueryDslPredicateExecutor<MyUser> {

}
