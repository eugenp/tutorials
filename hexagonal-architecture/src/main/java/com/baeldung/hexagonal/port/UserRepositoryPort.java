package com.baeldung.hexagonal.port;

import com.baeldung.hexagonal.domain.User;

public interface UserRepositoryPort {

    void add(User user);

    User getDetail(String userId);
}
