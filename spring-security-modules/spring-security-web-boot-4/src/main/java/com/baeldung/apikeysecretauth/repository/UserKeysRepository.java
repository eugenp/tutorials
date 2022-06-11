package com.baeldung.apikeysecretauth.repository;

import org.springframework.data.repository.CrudRepository;

import com.baeldung.apikeysecretauth.repository.model.UserKeysData;

public interface UserKeysRepository extends CrudRepository<UserKeysData, String> {
}
