package com.baeldung.boot.csfle.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LocalKmsUtils {

    private static final Logger log = LoggerFactory.getLogger(LocalKmsUtils.class);
    private static final int KEY_SIZE = 96;

    private LocalKmsUtils() {
    }

    public static byte[] createMasterKey(String path) {
        byte[] masterKey = new byte[KEY_SIZE];
        new SecureRandom().nextBytes(masterKey);

        try (FileOutputStream stream = new FileOutputStream(path)) {
            stream.write(masterKey);
        } catch (IOException e) {
            log.error("generating master key", e);
        }

        return masterKey;
    }

    public static byte[] readMasterKey(String path) {
        byte[] masterKey = new byte[KEY_SIZE];

        try (FileInputStream stream = new FileInputStream(path)) {
            stream.read(masterKey, 0, KEY_SIZE);
        } catch (IOException e) {
            log.error("reading master key", e);
        }

        return masterKey;
    }

    public static Map<String, Map<String, Object>> providersMap(String masterKeyPath) {
        if (masterKeyPath == null)
            throw new IllegalArgumentException("master key path cannot be null");

        File masterKeyFile = new File(masterKeyPath);
        byte[] masterKey;
        if (masterKeyFile.isFile()) {
            masterKey = readMasterKey(masterKeyPath);
        } else {
            masterKey = createMasterKey(masterKeyPath);
        }

        return new HashMap<String, Map<String, Object>>() {
            private static final long serialVersionUID = 1L;
            {
                put("local", new HashMap<String, Object>() {
                    private static final long serialVersionUID = 1L;
                    {
                        put("key", masterKey);
                    }
                });
            }
        };
    }
}
