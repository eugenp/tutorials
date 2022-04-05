package com.baeldung.interview;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StringInternUnitTest {
    @Test
    public void whenCallingStringIntern_thenStringsInterned() {
        String s1 = "Baeldung";
        String s2 = new String("Baeldung");
        String s3 = new String("Baeldung").intern();
         
        assertThat(s1 == s2).isFalse();
        assertThat(s1 == s3).isTrue();
    }
}
