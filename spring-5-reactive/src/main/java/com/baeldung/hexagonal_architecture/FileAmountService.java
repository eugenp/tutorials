package com.baeldung.hexagonal_architecture;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.stream.Collectors;

public class FileAmountService implements IAmountService {
    Map<String, Integer> map;

    //The file must be a JSON file in the form city: value located in the classpath.
    public FileAmountService(String fileName) {
        try {
            String content = Files.lines(Paths.get(this.getClass().getClassLoader().getResource(fileName).toURI())).collect(Collectors.joining(""));
            ObjectMapper om = new ObjectMapper();
            map = om.readValue(content, new TypeReference<Map<String, Integer>>() {});
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getAmount(String city) {
        return map.getOrDefault(city, -1);
    }
}
