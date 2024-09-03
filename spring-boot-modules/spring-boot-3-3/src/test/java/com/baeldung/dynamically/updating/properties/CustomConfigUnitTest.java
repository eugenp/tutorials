package com.baeldung.dynamically.updating.properties;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.baeldung.dynamically.updating.properties.utility.MyService;

import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = UpdatingPropertiesApplication.class)
public class CustomConfigUnitTest {

    @Autowired
    private ApplicationContext context;

    @Test
    void whenPropertyInjected_thenServiceUsesCustomProperty() {
        MyService service = context.getBean(MyService.class);
        assertEquals("default", service.getProperty());
    }

    @Test
    void whenPropertyChanged_thenServiceUsesUpdatedProperty() {
        System.setProperty("custom.property", "updated");
        MyService service = context.getBean(MyService.class);
        assertEquals("updated", service.getProperty());
    }
}
