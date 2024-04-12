package com.baeldung.finalization_closeable_cleaner;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MyCloseableResourceClass implements AutoCloseable {

    private final FileInputStream fis;

    public MyCloseableResourceClass() throws FileNotFoundException {
        this.fis = new FileInputStream("src/main/resources/file.txt");

    }

    public int getByteLength() throws IOException {
        System.out.println("Some operation");
        return this.fis.readAllBytes().length;
    }
    @Override
    public void close() throws IOException {
        System.out.println("Finalized object");
        this.fis.close();
    }
}
