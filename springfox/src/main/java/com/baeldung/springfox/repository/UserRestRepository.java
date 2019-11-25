package com.baeldung.springfox.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.baeldung.springfox.model.User;

@RepositoryRestResource(collectionResourceRel = "users", path = "users")
public interface UserRestRepository extends CrudRepository<User, Long> {

}
