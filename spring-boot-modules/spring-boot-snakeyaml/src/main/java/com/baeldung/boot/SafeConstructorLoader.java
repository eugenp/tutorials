package com.baeldung.boot;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

public class SafeConstructorLoader {

    public MyCustomClass parseYaml(String yamlInput) {
        Yaml yaml = new Yaml(new Constructor(MyCustomClass.class));
        return yaml.load(yamlInput);
    }

}
