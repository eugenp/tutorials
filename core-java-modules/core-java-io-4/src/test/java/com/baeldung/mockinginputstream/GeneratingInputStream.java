package com.baeldung.mockinginputstream;

import java.io.IOException;
import java.io.InputStream;

public class GeneratingInputStream extends InputStream {
    private final int desiredSize;
    private int actualSize = 0;
    private final byte[] seed;

    public GeneratingInputStream(int desiredSize, String seed) {
        this.desiredSize = desiredSize;
        this.seed = seed.getBytes();
    }

    @Override
    public int read() {
        if (actualSize >= desiredSize) {
            return -1;
        }
        return seed[actualSize++ % seed.length];
    }
}
