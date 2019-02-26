package com.baeldung.boot.user;

import java.util.List;
import java.util.Set;

import com.baeldung.boot.domain.User;

public interface UserRepositoryCustom {
    List<User> findUserByEmails(Set<String> emails);
}
