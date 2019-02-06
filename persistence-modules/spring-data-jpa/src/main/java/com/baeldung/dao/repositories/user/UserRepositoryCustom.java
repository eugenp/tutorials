package com.baeldung.dao.repositories.user;

import java.util.List;
import java.util.Set;

import com.baeldung.domain.user.User;

public interface UserRepositoryCustom {
    List<User> findUserByEmails(Set<String> emails);
}
