package org.baeldung.spring.data.persistence.dao.user;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import org.baeldung.spring.data.persistence.model.User;

public interface UserRepositoryCustom {
    List<User> findUserByEmails(Set<String> emails);
    
    List<User> findAllUsersByPredicates(Collection<Predicate<User>> predicates);
}
