package com.baeldung.test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;

import com.google.common.collect.ImmutableList;

public class SourceSetsTest {

    @Test
    public void whenRunThenSuccess() {
        List<String> someStrings = ImmutableList.of("Baeldung", "is", "cool");
        assertThat(true, is(true));
    }
}
