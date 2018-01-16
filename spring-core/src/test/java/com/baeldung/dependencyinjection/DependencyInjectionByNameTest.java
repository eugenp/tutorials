package com.baeldung.dependencyinjection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.baeldung.dependencyinjection.injectbyname.TextEditorSetterInjectionByName;

@ContextConfiguration(classes = { DependencyInjectionByNameTest.TestBeanConfig.class })
@RunWith(SpringRunner.class)
public class DependencyInjectionByNameTest {

    private TextEditorSetterInjectionByName injectionByName;

    @Autowired
    public void setInjectionByName(TextEditorSetterInjectionByName injectionByName) {
        this.injectionByName = injectionByName;
    }

    @Test
    public void testSetterBasedInjection() {
        assertNotNull(injectionByName.getDictionary());
        assertEquals("Hello from dictionary in French", injectionByName.getDictionary().hello());
    }

    public static class TestBeanConfig {

        @Bean
        public Dictionary englishDictionary() {
            return new Dictionary("English");
        }

        @Bean
        public Dictionary frenchDictionary() {
            return new Dictionary("French");
        }

        @Bean
        public TextEditorSetterInjectionByName textEditorSetterInjectionByName() {
            return new TextEditorSetterInjectionByName();
        }

    }
}
