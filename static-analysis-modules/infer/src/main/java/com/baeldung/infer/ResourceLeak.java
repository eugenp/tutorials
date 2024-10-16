package com.baeldung.infer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ResourceLeak {
    
    public static void main(String[] args) throws IOException {
        ResourceLeak.resourceLeak();
    }

    private static void resourceLeak() throws IOException {
        FileOutputStream stream;
        try {
            File file = new File("randomName.txt");
            stream = new FileOutputStream(file);
        } catch (IOException e) {
            return;
        }
        stream.write(0);
    }
}
