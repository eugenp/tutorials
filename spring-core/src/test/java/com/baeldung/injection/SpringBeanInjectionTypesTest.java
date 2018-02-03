package com.baeldung.injection;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = InjectionsApp.class)
public class SpringBeanInjectionTypesTest {

    @Autowired
    @Qualifier("preferences")
    private UserPreferences preferences;

    @Autowired
    @Qualifier("constructorPreferences")
    private UserPreferences constructorInjection;

    @Test
    public void givenSetterAndInterfaceTypes_WhenNotNull_ThenValid() {
        assertNotNull(preferences);
    }

    @Test
    public void givenConstructorTypes_WhenNotNull_ThenValid() {
        assertNotNull(constructorInjection.getName());
    }

}
