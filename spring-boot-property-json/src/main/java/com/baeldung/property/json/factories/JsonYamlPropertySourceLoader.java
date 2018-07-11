package com.baeldung.property.json.factories;

import org.springframework.beans.factory.config.YamlProcessor;
import org.springframework.boot.env.YamlPropertySourceLoader;

public class JsonYamlPropertySourceLoader extends YamlPropertySourceLoader{

    @Override
    public String[] getFileExtensions() {
        return new String[] {"json"};
        YamlProcessor
    }

}
