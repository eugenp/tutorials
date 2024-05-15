package com.baeldung.checkvowels;

import org.junit.jupiter.api.Test;

import static com.baeldung.checkvowels.CheckVowels.*;
import static org.assertj.core.api.Assertions.*;

class CheckVowelsUnitTest {

    @Test
    void givenAVowelCharacter_thenInVowelString() {
        assertThat(isInVowelsString('e')).isTrue();
    }

    @Test
    void givenAConsonantCharacter_thenNotInVowelString() {
        assertThat(isInVowelsString('z')).isFalse();
    }

    @Test
    void givenAVowelString_thenInVowelString() {
        assertThat(isInVowelsString("e")).isTrue();
    }

    @Test
    void givenAConsonantString_thenNotInVowelString() {
        assertThat(isInVowelsString("z")).isFalse();
    }

    @Test
    void givenAVowelCharacter_thenInVowelSwitch() {
        assertThat(isVowelBySwitch('e')).isTrue();
    }

    @Test
    void givenAConsonantCharacter_thenNotInVowelSwitch() {
        assertThat(isVowelBySwitch('z')).isFalse();
    }

    @Test
    void givenAVowelString_thenInVowelPattern() {
        assertThat(isVowelByRegex("e")).isTrue();
        assertThat(isVowelByRegex("E")).isTrue();
    }

    @Test
    void givenAVowelCharacter_thenInVowelPattern() {
        assertThat(isVowelByRegex(Character.toString('e'))).isTrue();
        assertThat(isVowelByRegex("E")).isTrue();
    }
}
