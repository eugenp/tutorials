package com.baeldung.filereadmethods;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class FileIOUtil {

    private static final Logger LOG = LoggerFactory.getLogger(FileIOUtil.class);

    public static String readFileFromResource(String filePath) {
        try (InputStream inputStream = FileIOUtil.class.getResourceAsStream(filePath)) {
            String result = null;
            if (inputStream != null) {
                result = new BufferedReader(new InputStreamReader(inputStream))
                  .lines()
                    .collect(Collectors.joining("\n"));
            }
            return result;
        } catch (IOException e) {
            LOG.error("Error reading file:", e);
            return null;
        }
    }

    public static String readFileFromFileSystem(String filePath) {
        try (InputStream inputStream = Files.newInputStream(Paths.get(filePath))) {
            return new BufferedReader(new InputStreamReader(inputStream))
              .lines()
                .collect(Collectors.joining("\n"));
        } catch (IOException e) {
            LOG.error("Error reading file:", e);
            return null;
        }
    }

}
