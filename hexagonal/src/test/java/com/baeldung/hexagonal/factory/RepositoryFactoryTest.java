package com.baeldung.hexagonal.factory;

import org.junit.jupiter.api.Test;

import com.baeldung.hexagonal.repository.UserRepository;

class RepositoryFactoryTest {

    @Test
    void test() {
        UserRepository repo = RepositoryFactory.getUserRepository();
        assert(repo != null);
    }

}
