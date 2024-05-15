package com.baeldung.diffstringstringbuffer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class StringBufferUnitTest {
    @Test
    void whenStringBufferVariableIsReassigned_thenVariableRetainsOriginalMemoryAddress() {
        MemoryAddress memoryAddress = new MemoryAddress();
        StringBuffer stringBuffer = new StringBuffer("DownTown");
        long address1 = memoryAddress.getMemoryAddress(stringBuffer);
        stringBuffer.insert(0, "Coder");
        assertEquals(stringBuffer.toString(), "CoderDownTown");
        long address2 = memoryAddress.getMemoryAddress(stringBuffer);
        assertEquals(address1, address2);
    }
}
