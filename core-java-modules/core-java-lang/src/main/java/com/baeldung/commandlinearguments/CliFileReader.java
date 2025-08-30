package com.baeldung.commandlinearguments;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class CliFileReader {

    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.out.println("Usage: CliFileReader <file-path>");
        }
        String path = args[0];
        List<String> lines = Files.readAllLines(Path.of(path));
        for (String line : lines) {
            System.out.println(line);

        }

    }

}
