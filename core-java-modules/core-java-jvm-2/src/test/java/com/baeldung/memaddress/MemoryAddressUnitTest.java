package com.baeldung.memaddress;

import org.junit.Test;
import org.openjdk.jol.vm.VM;

public class MemoryAddressUnitTest {

    @Test
    public void printTheMemoryAddress() {
        String answer = "42";

        System.out.println("The memory address is " + VM.current().addressOf(answer));
    }

    @Test
    public void identityHashCodeAndMemoryAddress() {
        Object obj = new Object();

        System.out.println("Memory address: " + VM.current().addressOf(obj));
        System.out.println("hashCode: " + obj.hashCode());
        System.out.println("hashCode: " + System.identityHashCode(obj));
        System.out.println("toString: " + obj);
    }
}
