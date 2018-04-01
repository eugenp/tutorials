package com.baeldung.loadresource;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

@Component
public class ResourceLoaderSample {

    private final ResourceLoader resourceLoader;

    @Autowired
    public ResourceLoaderSample(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public String loadFile(String fileName) {
        StringBuilder fileContent = new StringBuilder("");
        try {
            Resource resource = resourceLoader.getResource(fileName);

            File file = resource.getFile();
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                fileContent.append(line)
                    .append("\n");
            }
            scanner.close();
        } catch (IOException e) {
            System.out.println(e);
            return "";
        }
        return fileContent.toString();
    }
}
