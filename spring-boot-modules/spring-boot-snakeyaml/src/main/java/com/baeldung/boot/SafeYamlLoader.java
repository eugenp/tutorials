package com.baeldung.boot;

import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.SafeConstructor;

import java.util.Map;

@Service
public class SafeYamlLoader {

    public Map<String, Object> loadYamlSafely(String yamlContent) {
        // Pass LoaderOptions to SafeConstructor
        Yaml yaml = new Yaml(new SafeConstructor());
        return yaml.load(yamlContent);
    }

}
