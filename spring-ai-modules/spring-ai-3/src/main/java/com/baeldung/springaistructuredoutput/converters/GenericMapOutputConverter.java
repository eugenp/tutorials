package com.baeldung.springaistructuredoutput.converters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.github.victools.jsonschema.generator.*;
import com.github.victools.jsonschema.module.jackson.JacksonModule;
import org.springframework.ai.converter.StructuredOutputConverter;
import org.springframework.lang.NonNull;

import java.util.HashMap;
import java.util.Map;

public class GenericMapOutputConverter<V> implements StructuredOutputConverter<Map<String, V>> {

    private final ObjectMapper objectMapper;
    private final String jsonSchema;
    private final TypeReference<Map<String, V>> typeRef;

    public GenericMapOutputConverter(Class<V> valueType) {
        this.objectMapper = this.getObjectMapper();
        this.typeRef = new TypeReference<>() {};
        this.jsonSchema = generateJsonSchemaForValueType(valueType);
    }

    public Map<String, V> convert(@NonNull String text) {
        try {
            text = trimMarkdown(text);

            return objectMapper.readValue(text, typeRef);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert JSON to Map<String, V>", e);
        }
    }

    public String getFormat() {
        String raw = "Your response should be in JSON format.\nThe data structure for the JSON should match this Java class: %s\n" +
            "For the map values, here is the JSON Schema instance your output must adhere to:\n```%s```\n" +
            "Do not include any explanations, only provide a RFC8259 compliant JSON response following this format without deviation.\n";
        return String.format(raw, HashMap.class.getName(), this.jsonSchema);
    }

    protected ObjectMapper getObjectMapper() {
        return JsonMapper.builder()
          .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
          .build();
    }

    private String trimMarkdown(String text) {
        if (text.startsWith("```json") && text.endsWith("```")) {
            text = text.substring(7, text.length() - 3);
        }
        return text;
    }

    private String generateJsonSchemaForValueType(Class<V> valueType) {
        try {
            JacksonModule jacksonModule = new JacksonModule();
            SchemaGeneratorConfig config = new SchemaGeneratorConfigBuilder(SchemaVersion.DRAFT_2020_12, OptionPreset.PLAIN_JSON)
              .with(jacksonModule)
              .build();
            SchemaGenerator generator = new SchemaGenerator(config);

            JsonNode jsonNode = generator.generateSchema(valueType);
            ObjectWriter objectWriter = new ObjectMapper().writer(new DefaultPrettyPrinter().withObjectIndenter(new DefaultIndenter().withLinefeed(System.lineSeparator())));

            return objectWriter.writeValueAsString(jsonNode);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Could not generate JSON schema for value type: " + valueType.getName(), e);
        }
    }
}
