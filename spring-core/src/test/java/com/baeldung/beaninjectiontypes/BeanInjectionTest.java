package com.baeldung.beaninjectiontypes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.baeldung.beaninjectiontypes.ConstructorInjection;
import com.baeldung.beaninjectiontypes.FieldInjection;
import com.baeldung.beaninjectiontypes.MethodInjection;
import com.baeldung.dependencyinjectiontypes.TextFormatter;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = { ConstructorInjection.class, MethodInjection.class, FieldInjection.class, TextFormatter.class })
public class BeanInjectionTest {

    @Autowired
    private ConstructorInjection conatinsConstructorInjectedBean;

    @Autowired
    private MethodInjection containsMethodInjectedBean;

    @Autowired
    private FieldInjection containsFieldInjectedBean;

    @Test
    public void givenBean_WhenSetOnConstructor_ThenDependencyResolved() {
        assertNotNull(conatinsConstructorInjectedBean.getFormatter());
        assertEquals("TEXT FORMATTER IS CONSTRUCTOR INJECTED", containsMethodInjectedBean.format("Text Formatter is Constructor Injected"));
    }

    @Test
    public void givenBean_WhenSetOnMethod_ThenDependencyResolved() {
        assertNotNull(containsMethodInjectedBean.getFormatter());
        assertEquals("TEXT FORMATTER IS METHOD INJECTED", containsMethodInjectedBean.format("Text Formatter is Method Injected"));
    }

    @Test
    public void givenBean_WhenSetOnField_ThenDependencyResolved() {
        assertNotNull(containsFieldInjectedBean.getFormatter());
        assertEquals("TEXT FORMATTER IS FIELD INJECTED", containsFieldInjectedBean.format("Text Formatter is Field Injected"));
    }
}