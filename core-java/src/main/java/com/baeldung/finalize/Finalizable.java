package com.baeldung.finalize;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author zn.wang
 */
public class Finalizable {
    private BufferedReader reader;

    public Finalizable() {
        InputStream input =
                this.getClass()
                .getClassLoader()
                .getResourceAsStream("file.txt");
        reader = new BufferedReader(new InputStreamReader(input));
    }

    public String readFirstLine() throws IOException {
        String firstLine = reader.readLine();
        return firstLine;
    }

    /**
     * jvm进行gc时，会执行{@link java.lang.Object#finalize()}
     */
    @Override
    public void finalize() {
        try {
            reader.close();
            System.out.println("Closed BufferedReader in the finalizer");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
