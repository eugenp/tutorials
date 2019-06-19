package com.baeldung.core.resourcenotfound;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

class ResourceFileReader {
    private ArrayList<String> codes;

    ResourceFileReader(String path) {
        codes = init(path);
    }

    private ArrayList<String> init(String path) {
        ArrayList<String> codes = new ArrayList<>();
        Scanner scanner = new Scanner(getInputStream(path));
        while (scanner.hasNext()) {
            codes.add(scanner.nextLine());
        }
        return codes;
    }

    boolean isCodeValid(String code) {
        return codes.contains(code);
    }

    private InputStream getInputStream(String filePath) {
        return ClassLoader.getSystemClassLoader().getResourceAsStream(filePath);
    }

}
