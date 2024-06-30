package com.baeldung.testcontainers.jdbc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JdbcSupportLiveTest {

    @Autowired
    HogwardsRepository repo;

    @Test
    void t() {
        System.out.println(repo.findAll());
    }

}
