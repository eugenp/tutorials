package com.baeldung.autowire.sample;

import org.junit.Assert;
import org.junit.Test;

public class ServiceWithFieldInjectionUnitTest {

    @Test
    public void whenInjectCollaborator_thenUse() {
        // Object creation will be in an invalid state.
        ServiceWithFieldInjection serviceWithFieldInjection = new ServiceWithFieldInjection();
        Assert.assertEquals("collaborator used", serviceWithFieldInjection.useCollaborator());
    }
}
