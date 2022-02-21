package com.baeldung.filetomap;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileToHashMap {

    enum DupKeyOption {
        OVERWRITE, DISCARD
    }

    public static Map<String, String> byBufferedReader(String filePath, DupKeyOption dupKeyOption) {
        HashMap<String, String> map = new HashMap<>();
        String line;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            while ((line = reader.readLine()) != null) {
                String[] keyValuePair = line.split(":", 2);
                if (keyValuePair.length > 1) {
                    String key = keyValuePair[0];
                    String value = keyValuePair[1];
                    if (DupKeyOption.OVERWRITE == dupKeyOption) {
                        map.put(key, value);
                    } else if (DupKeyOption.DISCARD == dupKeyOption) {
                        map.putIfAbsent(key, value);
                    }
                } else {
                    System.out.println("No Key:Value found in line, ignoring: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static Map<String, String> byStream(String filePath, DupKeyOption dupKeyOption) {
        Map<String, String> map = new HashMap<>();
        try (Stream<String> lines = Files.lines(Paths.get(filePath))) {
            lines.filter(line -> line.contains(":"))
                .forEach(line -> {
                    String[] keyValuePair = line.split(":", 2);
                    String key = keyValuePair[0];
                    String value = keyValuePair[1];
                    if (DupKeyOption.OVERWRITE == dupKeyOption) {
                        map.put(key, value);
                    } else if (DupKeyOption.DISCARD == dupKeyOption) {
                        map.putIfAbsent(key, value);
                    }
                });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static Map<String, List<String>> aggregateByKeys(String filePath) {
        Map<String, List<String>> map = new HashMap<>();
        try (Stream<String> lines = Files.lines(Paths.get(filePath))) {
            lines.filter(line -> line.contains(":"))
                .forEach(line -> {
                    String[] keyValuePair = line.split(":", 2);
                    String key = keyValuePair[0];
                    String value = keyValuePair[1];
                    if (map.containsKey(key)) {
                        map.get(key).add(value);
                    } else {
                        map.put(key, Stream.of(value).collect(Collectors.toList()));
                    }
                });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }
}
