package com.baeldung.itest;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;

import com.google.common.collect.ImmutableList;

public class SourceSetsItest {

    @Test
    public void whenRunThenFail() {
        List<String> someStrings = ImmutableList.of("Baeldung", "is", "cool");
        assertThat(false, is(true));
    }
}
