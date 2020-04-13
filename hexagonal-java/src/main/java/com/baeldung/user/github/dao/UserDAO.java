package com.baeldung.user.github.dao;

import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import com.baeldung.user.github.model.User;

/**
 * Interface to implement the basic database interactions to manage the users data 
 */
public interface UserDAO extends CrudRepository<User, UUID> {

}
