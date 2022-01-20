package com.baeldung.springdatawebsupport.application.repositories;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.baeldung.springdatawebsupport.application.entities.User;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long>, QuerydslPredicateExecutor<User> {

}
