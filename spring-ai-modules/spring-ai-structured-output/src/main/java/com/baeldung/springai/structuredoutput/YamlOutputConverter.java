package com.baeldung.springai.structuredoutput;

import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.converter.StructuredOutputConverter;
import tools.jackson.dataformat.yaml.YAMLMapper;

class YamlOutputConverter<T> implements StructuredOutputConverter<T> {

    private final YAMLMapper yamlMapper = YAMLMapper.builder().build();
    private final Class<T> targetType;

    YamlOutputConverter(Class<T> targetType) {
        this.targetType = targetType;
    }

    @Override
    public String getFormat() {
        String schema = new BeanOutputConverter<>(targetType).getJsonSchema();
        return """
            Return a YAML response that matches this JSON schema: %s
            Do not include any explanations or markdown code fences.
            """.formatted(schema);
    }

    @Override
    public T convert(String source) {
        return yamlMapper.readValue(source, targetType);
    }
}