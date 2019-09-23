package com.baeldung.dirtiescontext;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.MethodMode;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SpringDataRestApplication.class)
class DirtiesContextTest {

    @Autowired
    protected UserCache userCache;

    @DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
    @Test
    void testOne() {
        userCache.addUser("John Doe");
        userCache.printUserList("Test One");
    }

    @Test
    void testTwo() {
        userCache.printUserList("Test Two");
    }

    @Test
    void testThree() {
        userCache.addUser("Jane Doe");
        userCache.printUserList("Test Three");
    }

    @Test
    void testFour() {
        userCache.printUserList("Test Four");
    }

}
