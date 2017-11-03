package com.baeldung.injectiontypes;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.baelding.injectiontypes.BeanDefinition;
import com.baelding.injectiontypes.ConstructorInjection;
import com.baelding.injectiontypes.FieldInjection;
import com.baelding.injectiontypes.SetterInjection;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
  loader = AnnotationConfigContextLoader.class,
  classes = {BeanDefinition.class, ConstructorInjection.class, SetterInjection.class, FieldInjection.class})
public class TypesOfInjectionTest {
    
    @Autowired
    private ConstructorInjection constructorInjection;
    
    @Autowired
    private FieldInjection fieldInjection;
    
    @Autowired
    private SetterInjection setterInjection;
    
    @Test
    public void givenWiredDependencies_WhenExecutingGetBean_ThenDependenciesShouldBeAlreadyInjected() {
        assertEquals("Using constructor injection, we got these parameters Hello-Injection",constructorInjection.getParameters());
        assertEquals("Using field injection, we got these parameters Hello-Injection",fieldInjection.getParameters());
        assertEquals("Using setter injection, we got these parameters Hello-Injection",setterInjection.getParameters());
    }

}
