package com.baeldung.dependencyinjection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@ContextConfiguration(classes = { DepdencenyInjectionTest.TestBeanConfig.class })
@RunWith(SpringRunner.class)
public class DepdencenyInjectionTest {

    private TextEditorConstructorInjected constructorInjected;

    private TextEditorFieldInjected fieldInjected;

    private TextEditorSetterInjected setterInjected;

    @Autowired
    public void setConstructorInjected(TextEditorConstructorInjected constructorInjected) {
        this.constructorInjected = constructorInjected;
    }

    @Autowired
    public void setFieldInjected(TextEditorFieldInjected fieldInjected) {
        this.fieldInjected = fieldInjected;
    }

    @Autowired
    public void setSetterInjected(TextEditorSetterInjected setterInjected) {
        this.setterInjected = setterInjected;
    }

    @Test
    public void testConstructorBasedInjection() {
        assertNotNull(constructorInjected.getDictionary());
        assertEquals("Hello from dictionary", constructorInjected.getDictionary().hello());
    }

    @Test
    public void testFieldBasedInjection() {
        assertNotNull(fieldInjected.getDictionary());
        assertEquals("Hello from dictionary", fieldInjected.getDictionary().hello());
    }

    @Test
    public void testSetterBasedInjection() {
        assertNotNull(setterInjected.getDictionary());
        assertEquals("Hello from dictionary", setterInjected.getDictionary().hello());
    }

    public static class TestBeanConfig {

        @Bean
        @Primary
        public Dictionary dictionary() {
            return new Dictionary();
        }

        @Bean
        @Primary
        @Autowired
        public TextEditorConstructorInjected textEditorConstructorInjected(Dictionary dictionary) {
            return new TextEditorConstructorInjected(dictionary);
        }

        @Bean
        @Primary
        public TextEditorFieldInjected textEditorFieldInjected() {
            return new TextEditorFieldInjected();
        }

        @Bean
        @Primary
        public TextEditorSetterInjected textEditorSetterInjected() {
            return new TextEditorSetterInjected();
        }

    }
}
