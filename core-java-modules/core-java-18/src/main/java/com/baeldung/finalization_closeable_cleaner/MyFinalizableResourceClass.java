package com.baeldung.finalization_closeable_cleaner;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MyFinalizableResourceClass {
    private FileInputStream fis;

    public MyFinalizableResourceClass() throws FileNotFoundException {
        this.fis = new FileInputStream("src/main/resources/file.txt");
    }

    public int getByteLength() throws IOException {
        System.out.println("Some operation");
        return this.fis.readAllBytes().length;
    }

    @Override
    protected void finalize() throws Throwable {
        System.out.println("Finalized object");
        this.fis.close();
    }
}
