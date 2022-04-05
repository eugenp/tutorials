package com.baeldung.itest;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;

import com.baeldung.main.SourceSetsObject;
import com.google.common.collect.ImmutableList;

public class SourceSetsItest {

    @Test
    public void givenImmutableList_whenRun_ThenSuccess() {

        SourceSetsObject underTest = new SourceSetsObject("lorem", "ipsum");
        List<String> someStrings = ImmutableList.of("Baeldung", "is", "cool");

        assertThat(underTest.getUser(), is("lorem"));
        assertThat(underTest.getPassword(), is("ipsum"));
        assertThat(someStrings.size(), is(3));
    }
}
