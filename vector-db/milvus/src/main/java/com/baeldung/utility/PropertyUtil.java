package com.baeldung.utility;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertyUtil {

    private static Map<String, Properties> MAP_OF_FILE_PROPERTIES = new HashMap();

    public static String getVal(String key, String propertyFile) {
        if (MAP_OF_FILE_PROPERTIES.containsKey(propertyFile)) {
            return MAP_OF_FILE_PROPERTIES.get(propertyFile)
                .getProperty(key);
        } else {
            Properties properties = new Properties();
            try (InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(
                PropertyUtil.class.getClassLoader()
                    .getResource(propertyFile)
                    .getPath()))) {
                properties.load(inputStreamReader);
                MAP_OF_FILE_PROPERTIES.put(propertyFile, properties);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return properties.getProperty(key);
        }
    }

}
