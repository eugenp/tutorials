package com.baeldung.disableautowiring;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import com.baeldung.disableautowiring.thirdpartylib.TestBean;

//@SpringBootTest
class DisableAutowireLiveTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void whenTestBeanIsCraetedWithBeanAnnotation_thenItShouldFail() {
        TestBean testBean = applicationContext.getBean(TestBean.class);
        Assertions.assertNotNull(testBean);
    }

}
