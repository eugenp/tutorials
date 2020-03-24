package com.baeldung.spring.cloud.archaius.basic;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.netflix.config.DynamicPropertyFactory;
import com.netflix.config.DynamicStringProperty;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ArchaiusBasicConfigurationIntegrationTest {

    @Autowired
    ConfigurableApplicationContext context;

    private DynamicStringProperty testPropertyWithDynamic = DynamicPropertyFactory.getInstance()
        .getStringProperty("baeldung.archaius.test.properties.one", "not found!");

    @Test
    public void givenIntialPropertyValue_whenPropertyChanges_thenArchaiusRetrievesNewValue() {
        String initialValue = testPropertyWithDynamic.get();

        TestPropertyValues.of("baeldung.archaius.test.properties.one=new-value")
            .applyTo(context);
        context.publishEvent(new EnvironmentChangeEvent(Collections.singleton("baeldung.archaius.test.properties.one")));
        String finalValue = testPropertyWithDynamic.get();

        assertThat(initialValue).isEqualTo("test-one");
        assertThat(finalValue).isEqualTo("new-value");
    }

}
