package com.baeldung.lastncharacters;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LastNCharactersUnitTest {

    private String s;
    private int n;

    @BeforeEach
    void init() {
        s = "10-03-2024";
        n = 4;
    }

    @Test
    void givenString_whenUsingIntStreamAsStreamSource_thenObtainLastNCharacters() {
        String result = s.chars()
            .mapToObj(c -> (char) c)
            .skip(s.length() - n)
            .map(String::valueOf)
            .collect(Collectors.joining());

        assertThat(result).isEqualTo("2024");
    }

    @Test
    void givenString_whenUsingOneArgSubstringMethod_thenObtainLastNCharacters() {
        int beginIndex = s.length() - n;

        assertThat(s.substring(beginIndex)).isEqualTo("2024");
    }

    @Test
    void givenString_whenUsingStreamOfCharactersAsSource_thenObtainLastNCharacters() {
        String result = Arrays.stream(ArrayUtils.toObject(s.toCharArray()))
            .skip(s.length() - n)
            .map(String::valueOf)
            .collect(Collectors.joining());

        assertThat(result).isEqualTo("2024");
    }

    @Test
    void givenString_whenUsingStringUtilsRight_thenObtainLastNCharacters() {
        assertThat(StringUtils.right(s, n)).isEqualTo("2024");
    }

    @Test
    void givenString_whenUsingTwoArgSubstringMethod_thenObtainLastNCharacters() {
        int beginIndex = s.length() - n;
        String result = s.substring(beginIndex, s.length());

        assertThat(result).isEqualTo("2024");
    }
}