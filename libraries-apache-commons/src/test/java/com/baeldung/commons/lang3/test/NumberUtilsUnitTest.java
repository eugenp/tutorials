package com.baeldung.commons.lang3.test;

import org.apache.commons.lang3.math.NumberUtils;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;

public class NumberUtilsUnitTest {
    
    @Test
    public void givenNumberUtilsClass_whenCalledcompareWithIntegers_thenCorrect() {
        assertThat(NumberUtils.compare(1, 1)).isEqualTo(0);
    }
    
    @Test
    public void givenNumberUtilsClass_whenCalledcompareWithLongs_thenCorrect() {
        assertThat(NumberUtils.compare(1L, 1L)).isEqualTo(0);
    }
    
    @Test
    public void givenNumberUtilsClass_whenCalledcreateNumber_thenCorrect() {
        assertThat(NumberUtils.createNumber("123456")).isEqualTo(123456);
    }
    
    @Test
    public void givenNumberUtilsClass_whenCalledisDigits_thenCorrect() {
        assertThat(NumberUtils.isDigits("123456")).isTrue();
    }
    
    @Test
    public void givenNumberUtilsClass_whenCallemaxwithIntegerArray_thenCorrect() {
        int[] array = {1, 2, 3, 4, 5, 6};
        assertThat(NumberUtils.max(array)).isEqualTo(6);
    }
    
    @Test
    public void givenNumberUtilsClass_whenCalleminwithIntegerArray_thenCorrect() {
        int[] array = {1, 2, 3, 4, 5, 6};
        assertThat(NumberUtils.min(array)).isEqualTo(1);
    }
    
    @Test
    public void givenNumberUtilsClass_whenCalleminwithByteArray_thenCorrect() {
        byte[] array = {1, 2, 3, 4, 5, 6};
        assertThat(NumberUtils.min(array)).isEqualTo((byte) 1);
    }
}
