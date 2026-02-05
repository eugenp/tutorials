package com.baeldung.libraries.snakeyaml;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

public class SafeConstructorLoader {

    public Customer parseYaml(String yamlInput) {
        Yaml yaml = new Yaml(new Constructor(Customer.class));
        return yaml.load(yamlInput);
    }

}
