package com.baeldung.spring.data.persistence.repository;

import com.baeldung.spring.data.persistence.model.User;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

public interface UserRepositoryCustom {

    List<User> findUserByEmails(Set<String> emails);
    
    List<User> findAllUsersByPredicates(Collection<Predicate<User>> predicates);

}
