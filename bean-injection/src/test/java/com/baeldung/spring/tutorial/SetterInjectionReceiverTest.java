package com.baeldung.spring.tutorial;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Test class for the SetterInjectionReceiver.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration({ "classpath:/setter-spring-test-configuration.xml" })
public class SetterInjectionReceiverTest {

    /**
     * Setter Injection Receiver
     */
    @Autowired
    private SetterInjectionReceiver setterInjectionReceiver;

    /**
     * Test used to show the result of the injection.
     */
    @Test
    public void whenGetInjectedObjectName_thenGetValue() {
        // WHEN
        final String injectedValueName = this.setterInjectionReceiver.getSetterInjectedObjectName();

        // THEN
        Assert.assertEquals("Injected Object", injectedValueName);
    }

    /**
     * Setter for the {@link SetterInjectionReceiver}.
     * @param setterInjectionReceiver The SetterInjectionReceiver to set
     */
    public void setSetterInjectionReceiver(SetterInjectionReceiver setterInjectionReceiver) {
        this.setterInjectionReceiver = setterInjectionReceiver;
    }
}
