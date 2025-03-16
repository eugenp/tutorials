package com.baeldung.libraries.snakeyaml;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.SafeConstructor;

import java.util.Map;

public class SafeYamlLoader {

    public Map<String, Object> loadYamlSafely(String yamlContent) {
        // Pass LoaderOptions to SafeConstructor
        Yaml yaml = new Yaml(new SafeConstructor());
        return yaml.load(yamlContent);
    }

}
