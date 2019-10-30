package com.baeldung.crunch;

import static org.junit.Assert.assertEquals;

import org.apache.crunch.impl.mem.emit.InMemoryEmitter;
import org.junit.Test;

import com.google.common.collect.ImmutableList;

public class ToUpperCaseFnUnitTest {

    @Test
    public void givenString_whenToUpperCaseFnCalled_UpperCaseStringReturned() {
        InMemoryEmitter<String> emitter = new InMemoryEmitter<String>();

        new ToUpperCaseFn().process("input", emitter);

        assertEquals(ImmutableList.of("INPUT"), emitter.getOutput());
    }

}
