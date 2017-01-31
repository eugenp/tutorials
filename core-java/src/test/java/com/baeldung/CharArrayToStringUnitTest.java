package com.baeldung;

import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CharArrayToStringUnitTest {

    @Test
    public void givenCharArray_whenInvokingStringValueOf_thenShouldReturnString() {

        char[] charArray = { 'f', 'i', 'r', 's', 't', ' ', 'b', 'l', 'o', 'g' };

        String firstBlog = String.valueOf(charArray);

        assertThat(firstBlog).isEqualTo("first blog");
    }

    @Test
    public void givenCharArray_whenCallingStringValueOfSubArray_thenShouldReturnSubArrayString() {

        char[] charArray = { 'f', 'i', 'r', 's', 't', ' ', 'b', 'l', 'o', 'g' };

        String first = String.valueOf(charArray,0,5);

        assertThat(first).isEqualTo("first");
    }
}
