package com.baeldung.bitset;

import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.info.GraphLayout;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.BitSet;

public class Plotter {

    public static void main(String[] args) throws IOException {
        Path path = Paths.get("footprint.csv");
        try (BufferedWriter stream = Files.newBufferedWriter(path, StandardOpenOption.CREATE)) {
            stream.write("bits,bool,bitset\n");

            for (int i = 0; i <= 10_000_000; i += 500) {
                System.out.println("Number of bits => " + i);

                boolean[] ba = new boolean[i];
                BitSet bitSet = new BitSet(i);

                long baSize = ClassLayout.parseInstance(ba).instanceSize();
                long bitSetSize = GraphLayout.parseInstance(bitSet).totalSize();

                stream.write((i + "," + baSize + "," + bitSetSize + "\n"));

                if (i % 10_000 == 0) {
                    stream.flush();
                }
            }
        }
    }
}
