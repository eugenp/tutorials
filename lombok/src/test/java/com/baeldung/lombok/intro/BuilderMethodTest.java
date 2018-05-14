package com.baeldung.lombok.intro;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class BuilderMethodTest
{

    @Test
    public void givenBuilderMethod_ClientIsBuilt() {
        ImmutableClient testImmutableClient = ClientBuilder.builder().name("foo").id(1).build();
        assertThat("foo")
            .isEqualTo(testImmutableClient.getName());
        assertThat(1)
            .isEqualTo(testImmutableClient.getId());
    }
}
