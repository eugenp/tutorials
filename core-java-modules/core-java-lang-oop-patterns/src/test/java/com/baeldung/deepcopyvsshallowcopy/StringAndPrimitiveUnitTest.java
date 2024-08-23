package com.baeldung.deepcopyvsshallowcopy;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class StringAndPrimitiveUnitTest {

    @Test
    public void whenModifyingStringObject_thenCopyShouldNotChange() {
        //given
        String original = "Baeldung";
        String shallowCopy = original;
        String deepCopy = new String(original);

        //when
        original = "Java";

        //then
        assertThat(shallowCopy.hashCode()).isNotEqualTo(original.hashCode());
        assertThat(deepCopy.hashCode()).isNotEqualTo(original.hashCode());
    }

    @Test
    public void whenModifyingPrimitive_thenCopyShouldNotChange() {
        //given
        int original = 5;
        int shallowCopy = original;

        //when
        original = 7;

        //then
        assertThat(original).isNotEqualTo(shallowCopy);
    }

}
