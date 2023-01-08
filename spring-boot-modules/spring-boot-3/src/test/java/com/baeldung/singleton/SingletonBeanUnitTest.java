package com.baeldung.singleton;

import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = SingletonApplication.class)
class SingletonBeanUnitTest {

    @Autowired
    @Qualifier("singletonBean")
    private SingletonBeanConfig.SingletonBean beanOne;

    @Autowired
    @Qualifier("singletonBean")
    private SingletonBeanConfig.SingletonBean beanTwo;

    @Autowired
    @Qualifier("anotherSingletonBean")
    private SingletonBeanConfig.SingletonBean beanThree;

    @Test
    void givenTwoBeansWithSameId_whenInjectingThem_thenSameObjectsAreReturned() {
        assertSame(beanOne, beanTwo);
    }

    @Test
    void givenTwoBeansWithDifferentId_whenInjectingThem_thenDifferentObjectsAreReturned() {
        assertNotSame(beanOne, beanThree);
    }

}
