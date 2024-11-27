package com.baeldung.boot;

import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;

@Service
public class VulnerableYamlLoader {
    public Object loadYaml(String yamlContent) {
        Yaml yaml = new Yaml();  // Default constructor - unsafe deserialization
        return yaml.load(yamlContent);
    }
}
