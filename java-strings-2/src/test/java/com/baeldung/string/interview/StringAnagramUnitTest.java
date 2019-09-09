package com.baeldung.string.interview;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;

import org.junit.Test;

public class StringAnagramUnitTest {
    public boolean isAnagram(String s1, String s2) {
        if(s1.length() != s2.length())
            return false;
            
        char[] arr1 = s1.toCharArray();
        char[] arr2 = s2.toCharArray();
        
        Arrays.sort(arr1);
        Arrays.sort(arr2);
        
        return Arrays.equals(arr1, arr2);
    }
    
    @Test
    public void whenTestAnagrams_thenTestingCorrectly() {
        assertThat(isAnagram("car", "arc")).isTrue();
        assertThat(isAnagram("west", "stew")).isTrue();
        assertThat(isAnagram("west", "east")).isFalse();
    }
}
