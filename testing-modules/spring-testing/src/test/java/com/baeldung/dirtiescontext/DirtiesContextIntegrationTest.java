package com.baeldung.dirtiescontext;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.MethodMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@TestMethodOrder(OrderAnnotation.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = SpringDataRestApplication.class)
class DirtiesContextIntegrationTest {

    @Autowired
    protected UserCache userCache;

    @Test
    @Order(1)
    void addJaneDoeAndPrintCache() {
        userCache.addUser("Jane Doe");
        userCache.printUserList("addJaneDoeAndPrintCache");
    }

    @Test
    @Order(2)
    void printCache() {
        userCache.printUserList("printCache");
    }

    @DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
    @Test
    @Order(3)
    void addJohnDoeAndPrintCache() {
        userCache.addUser("John Doe");
        userCache.printUserList("addJohnDoeAndPrintCache");
    }

    @Test
    @Order(4)
    void printCacheAgain() {
        userCache.printUserList("printCacheAgain");
    }

}
