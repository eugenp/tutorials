package com.baeldung.hexagonal.port;

import com.baeldung.domain.User;

public interface UserDBPort {

    void register(String name);

    User getUser(Integer id);

}
