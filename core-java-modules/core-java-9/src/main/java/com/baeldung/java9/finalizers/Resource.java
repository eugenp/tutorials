package com.bealdung.java9.finalizers;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

class Resource implements AutoCloseable {

    final BufferedReader reader;

    public Resource(String filename) throws FileNotFoundException {
        reader = new BufferedReader(new FileReader(filename));
    }

    public long getLineNumber() {
        return reader.lines()
            .count();
    }

    @Override
    public void close() throws Exception {
        reader.close();
        System.out.println("BufferedReader resource closed");
    }
}