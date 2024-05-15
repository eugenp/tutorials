package com.baeldung.commons.lang3.test;

import org.apache.commons.lang3.StringUtils;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;

public class StringUtilsUnitTest {
    
    @Test
    public void givenStringUtilsClass_whenCalledisBlank_thenCorrect() {
        assertThat(StringUtils.isBlank(" ")).isTrue();
    }
    
    @Test
    public void givenStringUtilsClass_whenCalledisEmpty_thenCorrect() {
        assertThat(StringUtils.isEmpty("")).isTrue();
    }
    
    @Test
    public void givenStringUtilsClass_whenCalledisAllLowerCase_thenCorrect() {
        assertThat(StringUtils.isAllLowerCase("abd")).isTrue();
    }
    
    @Test
    public void givenStringUtilsClass_whenCalledisAllUpperCase_thenCorrect() {
        assertThat(StringUtils.isAllUpperCase("ABC")).isTrue();
    }
    
    @Test
    public void givenStringUtilsClass_whenCalledisMixedCase_thenCorrect() {
        assertThat(StringUtils.isMixedCase("abC")).isTrue();
    }
    
    @Test
    public void givenStringUtilsClass_whenCalledisAlpha_thenCorrect() {
        assertThat(StringUtils.isAlpha("abc")).isTrue();
    }
    
    @Test
    public void givenStringUtilsClass_whenCalledisAlphanumeric_thenCorrect() {
        assertThat(StringUtils.isAlphanumeric("abc123")).isTrue();
    }
    
    @Test
    public void givenStringUtilsClass_whenCalledcontains_thenCorrect() {
        assertThat(StringUtils.contains("abc", "ab")).isTrue();
    }
    
    @Test
    public void givenStringUtilsClass_whenCalledcontainsAny_thenCorrect() {
        assertThat(StringUtils.containsAny("abc", 'a', 'b', 'c')).isTrue();
    }
    
    @Test
    public void givenStringUtilsClass_whenCalledcontainsIgnoreCase_thenCorrect() {
        assertThat(StringUtils.containsIgnoreCase("abc", "ABC")).isTrue();
    }
    
    @Test
    public void givenStringUtilsClass_whenCalledswapCase_thenCorrect() {
        assertThat(StringUtils.swapCase("abc")).isEqualTo("ABC");
    }
    
    @Test
    public void givenStringUtilsClass_whenCalledreverse_thenCorrect() {
        assertThat(StringUtils.reverse("abc")).isEqualTo("cba");
    }
    
    @Test
    public void givenStringUtilsClass_whenCalleddifference_thenCorrect() {
        assertThat(StringUtils.difference("abc", "abd")).isEqualTo("d");
    }
}
