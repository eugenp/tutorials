package com.baeldung.splitstringtointarray;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class StringToIntArrayConverterUnitTest {

    private final SplitStringToIntArray converter = new SplitStringToIntArray();

    @Test
    void givenCommaSeparatedString_whenConvert_thenReturnIntArray() {
        int[] result = converter.convert("10, 20, 30, 40, 50", ",");
        assertThat(result).containsExactly(10, 20, 30, 40, 50);
    }

    @Test
    void givenSemicolonSeparatedString_whenConvert_thenReturnIntArray() {
        int[] result = converter.convert("10; 20; 30; 40; 50", ";");
        assertThat(result).containsExactly(10, 20, 30, 40, 50);
    }

    @Test
    void givenPipeSeparatedString_whenConvert_thenReturnIntArray() {
        int[] result = converter.convert("10|20|30|40|50", "\\|");
        assertThat(result).containsExactly(10, 20, 30, 40, 50);
    }

    @Test
    void givenEmptyString_whenConvert_thenReturnEmptyArray() {
        int[] result = converter.convert("", ",");
        assertThat(result).isEmpty();
    }
}
