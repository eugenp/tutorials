package com.baeldung.groovyconfig;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.baeldung.groovyconfig.JavaBeanConfig;
import com.baeldung.groovyconfig.JavaPersonBean;

public class JavaConfigurationUnitTest {

    @Test
    public void whenJavaConfig_thenCorrectPerson() {

        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(JavaBeanConfig.class);
        ctx.refresh();

        JavaPersonBean j = ctx.getBean(JavaPersonBean.class);

        assertEquals("31", j.getAge());
        assertEquals("green", j.getEyesColor());
        assertEquals("blond", j.getHairColor());

    }
}
