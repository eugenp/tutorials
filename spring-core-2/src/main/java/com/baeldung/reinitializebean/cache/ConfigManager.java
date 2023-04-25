package com.baeldung.reinitializebean.cache;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Service("ConfigManager")
public class ConfigManager {

    private static final Log LOG = LogFactory.getLog(ConfigManager.class);

    private Map<String,Object> config;

    private final String filePath;

    public ConfigManager(@Value("${config.file.path}") String filePath) {
        this.filePath = filePath;
        initConfigs();
    }

    private void initConfigs() {
        Properties properties = new Properties();
        try {
            properties.load(Files.newInputStream(Paths.get(filePath)));
        } catch (IOException e) {
            LOG.error("Error loading configuration:", e);
        }
        config = new HashMap<>();
        for (Map.Entry<Object, Object> entry : properties.entrySet()) {
            config.put(String.valueOf(entry.getKey()), entry.getValue());
        }
    }

    public Object getConfig(String key) {
        return config.get(key);
    }

    public void reinitializeConfig() {
        initConfigs();
    }

}
