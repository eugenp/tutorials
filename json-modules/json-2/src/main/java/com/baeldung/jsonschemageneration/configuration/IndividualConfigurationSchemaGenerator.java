package com.baeldung.jsonschemageneration.configuration;

import com.baeldung.jsonschemageneration.recursive.Author;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.victools.jsonschema.generator.Option;
import com.github.victools.jsonschema.generator.OptionPreset;
import com.github.victools.jsonschema.generator.SchemaGenerator;
import com.github.victools.jsonschema.generator.SchemaGeneratorConfig;
import com.github.victools.jsonschema.generator.SchemaGeneratorConfigBuilder;
import com.github.victools.jsonschema.generator.SchemaVersion;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class IndividualConfigurationSchemaGenerator {
    public static void main(String[] args) {

        SchemaGeneratorConfigBuilder configBuilder = new SchemaGeneratorConfigBuilder(SchemaVersion.DRAFT_2020_12, OptionPreset.PLAIN_JSON);

        configBuilder.forFields().withRequiredCheck(field -> field.getAnnotationConsideringFieldAndGetter(Nullable.class) == null).withArrayUniqueItemsResolver(scope -> scope.getType().getErasedType() == (List.class) ? true : null);

        configBuilder.forMethods().withRequiredCheck(method -> method.getAnnotationConsideringFieldAndGetter(NotNull.class) != null);

        configBuilder.forTypesInGeneral()
          .withArrayUniqueItemsResolver(scope -> scope.getType().getErasedType() == (List.class) ? true : null)
          .withDefaultResolver(scope -> scope.getType().getErasedType() == List.class ? Collections.EMPTY_LIST : null)
          .withDefaultResolver(scope -> scope.getType().getErasedType() == Date.class ? Date.from(Instant.now()) : null)
          .withEnumResolver(scope -> scope.getType().getErasedType().isEnum() ? Stream.of(scope.getType().getErasedType().getEnumConstants()).map(v -> ((Enum<?>) v).name()).collect(Collectors.toList()) : null);

        SchemaGeneratorConfig config = configBuilder.with(Option.EXTRA_OPEN_API_FORMAT_VALUES).build();

        SchemaGenerator generator = new SchemaGenerator(config);
        JsonNode jsonSchema = generator.generateSchema(Author.class);

        System.out.println(jsonSchema.toPrettyString());
    }

}
