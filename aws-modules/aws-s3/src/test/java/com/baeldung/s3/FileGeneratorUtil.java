package com.baeldung.s3;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

class FileGeneratorUtil {

    private static final String MAGIC_WORD = "baeldung";
    private static final ThreadLocalRandom RANDOM = ThreadLocalRandom.current();

    public static File generate() {
        String fileName = "test_" + UUID.randomUUID().toString() + ".txt";
        File file = new File(fileName);
        try (FileWriter writer = new FileWriter(file)) {
            for (int i = 0; i < RANDOM.nextInt(20) + 1; i++) {
                writer.write(MAGIC_WORD);
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return file;
    }

}