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

    @Test
    void whenAStringBufferIsCreated_thenItCanBeChanged() {
        StringBuffer stringBuffer = new StringBuffer("DownTown");
    }

    @Test
    public void stringBufferBenchmark() {
        MemoryAddress memoryAddress = new MemoryAddress();
        String[] userNameParts = {"Coder", "Cat", "Down", "Top", "Town"};
        StringBuffer userName = new StringBuffer();
        userName.append(userNameParts[2]).append(userNameParts[4]).append(userNameParts[0]);
        userName.delete(8, userName.length()).insert(0, "Coder");
    }
}
