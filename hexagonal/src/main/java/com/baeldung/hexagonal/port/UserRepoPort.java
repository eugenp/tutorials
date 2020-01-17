package com.baeldung.hexagonal.port;

import com.baeldung.hexagonal.domain.User;

public interface UserRepoPort {

    public void createUser(User user);

    public User getUser(Long userId);

}
