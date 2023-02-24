package com.baeldung.crunch;

import static org.junit.Assert.assertEquals;

import org.apache.crunch.PCollection;
import org.apache.crunch.impl.mem.MemPipeline;
import org.apache.crunch.types.writable.Writables;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

public class ToUpperCaseWithCounterFnUnitTest {

    @Before
    public void setUp() throws Exception {
        MemPipeline.clearCounters();
    }

    @Test
    public void whenFunctionCalled_counterIncementendForChangedValues() {
        PCollection<String> inputStrings = MemPipeline.collectionOf("This", "is", "a", "TEST", "string");
        PCollection<String> upperCaseStrings = inputStrings.parallelDo(new ToUpperCaseWithCounterFn(), Writables.strings());

        assertEquals(ImmutableList.of("THIS", "IS", "A", "TEST", "STRING"), Lists.newArrayList(upperCaseStrings.materialize()));
        assertEquals(4L, MemPipeline.getCounters()
            .findCounter("UpperCase", "modified")
            .getValue());
    }
}
