package com.baeldung.injection;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        loader = AnnotationConfigContextLoader.class,
        classes = Config.class
)
public class InjectionIntegrationTest {

    @Autowired
    private ApplicationContext ctx;

    @Test
    public void givenAutowiredOnConstructor_whenObjectCreated_thenDependencyResolved() {
        ConstructorDI constructorDI = ctx.getBean(ConstructorDI.class);
        Assert.assertEquals("Hello world from constructor injection", constructorDI.formatMessage());
    }

    @Test
    public void givenAutowiredOnField_whenObjectCreated_thenDependencyResolved() {
        FieldDI fieldDI = ctx.getBean(FieldDI.class);
        Assert.assertEquals("Hello world from field injection", fieldDI.formatMessage());
    }

    @Test
    public void givenAutowiredOnSetter_whenObjectCreated_thenDependencyResolved() {
        SetterDI setterDI = ctx.getBean(SetterDI.class);
        Assert.assertEquals("Hello world from setter injection", setterDI.formatMessage());
    }
}
