package com.baeldung.hexagonal.repository;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.baeldung.hexagonal.dto.User;

class FileBasedUserRepositoryTest {

    @Test
    void test() {
        UserRepository repo = new FileBasedUserRepository();
        List<User> userList = repo.getUsers();
        assert(userList != null && userList.size() > 0);
    }
}
