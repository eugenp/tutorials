package com.baeldung.di.types;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@ActiveProfiles("setter-inject")
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = SetterInjectConfiguration.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class SetterInjectTest {

    @Autowired
    private CodeEditor editor;

    @Test
    public void testConstructorInject() {
        assertNotNull(editor);
        assertNotNull(editor.getFormatter());
    }
}
