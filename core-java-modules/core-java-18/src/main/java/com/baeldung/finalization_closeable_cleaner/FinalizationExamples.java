package com.baeldung.finalization_closeable_cleaner;

import java.io.FileInputStream;
import java.io.IOException;

public class FinalizationExamples {
    FileInputStream fis = null;

    public void readFileOperationWithFinalization() throws IOException {
        try {
            fis = new FileInputStream("input.txt");
            // perform operation on the file
            System.out.println(fis.readAllBytes().length);

        } finally {
            if (fis != null)
                fis.close();
        }
    }

    public void readFileOperationWithTryWith() throws IOException {
        try (FileInputStream fis = new FileInputStream("input.txt")) {
            // perform operations
            System.out.println(fis.readAllBytes().length);
        }
    }
}
