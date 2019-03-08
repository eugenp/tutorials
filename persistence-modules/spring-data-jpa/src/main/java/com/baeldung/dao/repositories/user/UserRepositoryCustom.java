package com.baeldung.dao.repositories.user;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import com.baeldung.domain.user.User;

public interface UserRepositoryCustom {
    List<User> findUserByEmails(Set<String> emails);

    List<User> findAllUsersByPredicates(Collection<Predicate<User>> predicates);
}
