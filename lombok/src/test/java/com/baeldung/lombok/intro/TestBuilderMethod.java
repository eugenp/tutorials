package com.baeldung.lombok.intro;

import org.junit.jupiter.api.Test;

import static junit.framework.TestCase.assertEquals;

public class TestBuilderMethod {

    @Test
    public void givenBuilderMethod_ClientIsBuilt() {
        ImmutableClient testImmutableClient = ClientBuilder.builder().name("foo").id(1).build();
        assertEquals("foo", testImmutableClient.getName());
        assertEquals(1, testImmutableClient.getId());
    }
}
