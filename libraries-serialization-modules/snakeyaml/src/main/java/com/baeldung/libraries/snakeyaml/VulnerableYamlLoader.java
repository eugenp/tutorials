package com.baeldung.libraries.snakeyaml;

import org.yaml.snakeyaml.Yaml;

public class VulnerableYamlLoader {
    public Object loadYaml(String yamlContent) {
        Yaml yaml = new Yaml();  // Default constructor - unsafe deserialization
        return yaml.load(yamlContent);
    }
}
