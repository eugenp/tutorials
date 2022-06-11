package com.baeldung.apikeysecretauth.repository;

import org.springframework.data.repository.CrudRepository;

import com.baeldung.apikeysecretauth.repository.model.UserData;

public interface UserRepository extends CrudRepository<UserData, Long> {
}
