package com.baeldung.boot.csfle.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

public class LocalKmsUtils {

    private static final int KEY_SIZE = 96;

    private LocalKmsUtils() {
    }

    public static byte[] createMasterKey(String path) throws FileNotFoundException, IOException {
        byte[] masterKey = new byte[KEY_SIZE];
        new SecureRandom().nextBytes(masterKey);

        try (FileOutputStream stream = new FileOutputStream(path)) {
            stream.write(masterKey);
        }

        return masterKey;
    }

    public static byte[] readMasterKey(String path) throws FileNotFoundException, IOException {
        byte[] masterKey = new byte[KEY_SIZE];

        try (FileInputStream stream = new FileInputStream(path)) {
            stream.read(masterKey, 0, KEY_SIZE);
        }

        return masterKey;
    }

    public static Map<String, Map<String, Object>> providersMap(String masterKeyPath) throws FileNotFoundException, IOException {
        if (masterKeyPath == null)
            throw new IllegalArgumentException("master key path cannot be null");

        File masterKeyFile = new File(masterKeyPath);
        byte[] masterKey = masterKeyFile.isFile() 
            ? readMasterKey(masterKeyPath) 
            : createMasterKey(masterKeyPath);

        Map<String, Object> masterKeyMap = new HashMap<>();
        masterKeyMap.put("key", masterKey);
        Map<String, Map<String, Object>> providersMap = new HashMap<>();
        providersMap.put("local", masterKeyMap);
        return providersMap;
    }
}
