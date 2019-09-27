package com.baeldung.dirtiescontext;

import org.junit.FixMethodOrder;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.MethodMode;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SpringDataRestApplication.class)
class DirtiesContextIntegrationTest {

    @Autowired
    protected UserCache userCache;

    @Test
    void testA() {
        userCache.addUser("Jane Doe");
        userCache.printUserList("Test A");
    }

    @Test
    void testB() {
        userCache.printUserList("Test B");
    }

    @DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
    @Test
    void testC() {
        userCache.addUser("John Doe");
        userCache.printUserList("Test C");
    }

    @Test
    void testD() {
        userCache.printUserList("Test D");
    }

}
