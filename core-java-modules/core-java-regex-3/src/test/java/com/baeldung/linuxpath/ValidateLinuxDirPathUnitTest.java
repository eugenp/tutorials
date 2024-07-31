package com.baeldung.linuxpath;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class ValidateLinuxDirPathUnitTest {

    @Test
    void whenUsingRegex_thenSuccess() {
        String regex = "^/|(/[\\w-]+)+$";
        assertThat("/").matches(regex);
        assertThat("/foo").matches(regex);
        assertThat("/foo/0").matches(regex);
        assertThat("/foo/0/bar").matches(regex);
        assertThat("/f_o_o/-/bar").matches(regex);

        assertThat("").doesNotMatch(regex);
        assertThat("  ").doesNotMatch(regex);
        assertThat("foo").doesNotMatch(regex);
        assertThat("/foo/").doesNotMatch(regex);
        assertThat("/foo/bar/").doesNotMatch(regex);
        assertThat("/fo o/bar").doesNotMatch(regex);
        assertThat("/foo/b@ar").doesNotMatch(regex);
    }
}