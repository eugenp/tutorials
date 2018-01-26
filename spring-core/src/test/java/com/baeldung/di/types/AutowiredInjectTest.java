package com.baeldung.di.types;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.baeldung.di.types.auto.AutowiredFormatterConfiguration;
import com.baeldung.di.types.auto.CodeEditorAutowiredByConstructor;
import com.baeldung.di.types.auto.CodeEditorAutowiredByField;
import com.baeldung.di.types.auto.CodeEditorAutowiredBySetter;

@ActiveProfiles("autowired-inject")
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = AutowiredFormatterConfiguration.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class AutowiredInjectTest {

    @Autowired
    private CodeEditorAutowiredByConstructor autowiredByConstructor;

    @Autowired
    private CodeEditorAutowiredBySetter autowiredBySetter;
    
    @Autowired
    private CodeEditorAutowiredByField autowiredByField;
    
    @Test
    public void testConstructorInject() {
        assertNotNull(autowiredByConstructor);
        assertNotNull(autowiredByConstructor.getFormatter());
    }
    
    @Test
    public void testSetterInject() {
        assertNotNull(autowiredBySetter);
        assertNotNull(autowiredBySetter.getFormatter());
    }
    
    @Test
    public void testFieldInject() {
        assertNotNull(autowiredByField);
        assertNotNull(autowiredByField.getFormatter());
    }
}
