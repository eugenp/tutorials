package com.baeldung.reflection.check.abstractclass;

import java.lang.reflect.Modifier;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AbstractExampleUnitTest {

    @Test
    void whenCheckAbstractClass_ThenSuccess() throws Exception {
        Class<AbstractExample> clazz = AbstractExample.class;
        Assertions.assertTrue(Modifier.isAbstract(clazz.getModifiers()));
    }

}
