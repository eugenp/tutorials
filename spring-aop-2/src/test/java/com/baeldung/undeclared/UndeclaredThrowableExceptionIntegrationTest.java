package com.baeldung.undeclared;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.UndeclaredThrowableException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UndeclaredApplication.class)
public class UndeclaredThrowableExceptionIntegrationTest {

    @Autowired private UndeclaredService service;

    @Test
    public void givenAnAspect_whenCallingAdvisedMethod_thenShouldWrapTheException() {
        assertThatThrownBy(service::doSomething)
          .isInstanceOf(UndeclaredThrowableException.class)
          .hasCauseInstanceOf(SomeCheckedException.class);
    }
}
