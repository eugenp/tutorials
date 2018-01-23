package com.baeldung.spring.tutorial;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.baeldung.spring.tutorial.config.ConstructorSpringInjectionConfig;

/**
 * Test class for the ConstructorInjectionReceiver.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { ConstructorSpringInjectionConfig.class })
public class ConstructorInjectionReceiverTest {

    /**
     * Constructor Injection Receiver.
     */
    @Autowired
    private ConstructorInjectionReceiver constructorInjectionReceiver;

    /**
     * Test used to show the result of the injection.
     */
    @Test
    public void whenGetInjectedObjectName_thenGetValue() {
        // WHEN
        final String injectedValueName = this.constructorInjectionReceiver.getConstructorInjectedObjectName();

        // THEN
        Assert.assertEquals("Injected Object", injectedValueName);
    }

    /**
     * Setter for the {@link ConstructorInjectionReceiver}.
     * @param constructorInjectionReceiver the constructorInjectionReceiver to set
     */
    public void setConstructorInjectionReceiver(ConstructorInjectionReceiver constructorInjectionReceiver) {
        this.constructorInjectionReceiver = constructorInjectionReceiver;
    }
}
