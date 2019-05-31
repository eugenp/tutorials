package com.baeldung.hexagonalarch.port;

import com.baeldung.hexagonalarch.model.User;

public interface UserRepoPort {
    void create(User user);

    User getUser(Integer userid);
}
