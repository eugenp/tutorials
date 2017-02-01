package com.baeldung.autowire.sample;

import org.junit.Assert;
import org.junit.Test;

public class ServiceWithCIUnitTest {

    @Test
    public void whenInjectCollaborator_thenUse() {
        // Object will be created correctly.
        ServiceWithCI serviceWithCI = new ServiceWithCI(new Collaborator());
        Assert.assertEquals("collaborator used", serviceWithCI.useCollaborator());
    }
}
