package com.baeldung.diffstringstringbuffer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

public class StringUnitTest {
    @Test
    void whenStringVariableIsReassigned_thenVariableGetsNewMemoryAddress() {
        MemoryAddress memoryAddress = new MemoryAddress();
        String s1 = "DownTown";
        long address1 = memoryAddress.getMemoryAddress(s1);
        s1 = "Coder";
        long address2 = memoryAddress.getMemoryAddress(s1);
        assertNotEquals(address1, address2);
    }

    @Test
    void whenStringsAreIncludedInAStringBuffer_thenThoseStringsRemainImmutable() {
        String s1 = "TopCat";
        StringBuffer stringBuffer = new StringBuffer("DownTown");
        stringBuffer.append(s1);
        stringBuffer.reverse();
        assertNotEquals(s1, "taCpoT");
        stringBuffer.delete(0, 3);
        assertEquals(s1, "TopCat");
    }
}
