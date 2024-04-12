package com.baeldung.bitset;

import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.info.GraphLayout;

import java.util.BitSet;

public class Sizing {

    public static void main(String[] args) {
        boolean[] ba = new boolean[10_000];
        System.out.println(ClassLayout.parseInstance(ba).toPrintable());

        BitSet bitSet = new BitSet(10_000);
        System.out.println(GraphLayout.parseInstance(bitSet).toPrintable());
    }
}
