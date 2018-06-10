package com.baeldung.loadresource;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import org.springframework.stereotype.Component;

@Component
public class ClassLoaderSample {

    public String loadFile(String fileName) {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(fileName)
            .getFile());

        StringBuilder fileContent = new StringBuilder("");
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                fileContent.append(line)
                    .append("\n");
            }
            scanner.close();
        } catch (IOException e) {
            return "";
        }
        return fileContent.toString();
    }

}
