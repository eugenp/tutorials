package com.baeldung.commons.lang3.test;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;

public class HashCodeBuilderUnitTest {

    @Test
    public void givenHashCodeBuilderInstance_whenCalledtoHashCode_thenCorrect() {
        int hashcode = new HashCodeBuilder(17, 37)
            .append("John")
            .append("john@domain.com")
            .toHashCode();
        assertThat(hashcode).isEqualTo(1269178828);
    }
}
