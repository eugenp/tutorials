package com.baeldung.jsonschemageneration.configuration;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.victools.jsonschema.generator.Option;
import com.github.victools.jsonschema.generator.OptionPreset;
import com.github.victools.jsonschema.generator.SchemaGenerator;
import com.github.victools.jsonschema.generator.SchemaGeneratorConfig;
import com.github.victools.jsonschema.generator.SchemaGeneratorConfigBuilder;
import com.github.victools.jsonschema.generator.SchemaVersion;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AdvancedConfigurationSchemaGenerator {
    public static void main(String[] args) {

        SchemaGeneratorConfigBuilder configBuilder = new SchemaGeneratorConfigBuilder(SchemaVersion.DRAFT_2020_12, OptionPreset.PLAIN_JSON);

        configBuilder.forFields().withInstanceAttributeOverride((node, field, context) -> node.put("readOnly", field.getDeclaredType().isInstanceOf(UUID.class)));

        configBuilder.forFields()
          .withTargetTypeOverridesResolver(field -> Optional.ofNullable(field.getAnnotationConsideringFieldAndGetterIfSupported(AllowedTypes.class))
            .map(AllowedTypes::value)
            .map(Stream::of)
            .map(stream -> stream.map(subtype -> field.getContext().resolve(subtype)))
            .map(stream -> stream.collect(Collectors.toList()))
            .orElse(null));

        SchemaGeneratorConfig config = configBuilder.with(Option.EXTRA_OPEN_API_FORMAT_VALUES).build();

        SchemaGenerator generator = new SchemaGenerator(config);
        JsonNode jsonSchema = generator.generateSchema(AdvancedArticle.class);

        System.out.println(jsonSchema.toPrettyString());
    }

}
