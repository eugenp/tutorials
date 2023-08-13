package com.baeldung.jsonschemageneration.modules;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.victools.jsonschema.generator.OptionPreset;
import com.github.victools.jsonschema.generator.SchemaGenerator;
import com.github.victools.jsonschema.generator.SchemaGeneratorConfigBuilder;
import com.github.victools.jsonschema.generator.SchemaVersion;
import com.github.victools.jsonschema.module.jakarta.validation.JakartaValidationModule;
import com.github.victools.jsonschema.module.jakarta.validation.JakartaValidationOption;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class JakartaValidationModuleSchemaGenerator {
    public static void main(String[] args) {

        JakartaValidationModule module = new JakartaValidationModule(JakartaValidationOption.NOT_NULLABLE_FIELD_IS_REQUIRED, JakartaValidationOption.INCLUDE_PATTERN_EXPRESSIONS);
        SchemaGeneratorConfigBuilder configBuilder = new SchemaGeneratorConfigBuilder(SchemaVersion.DRAFT_2020_12, OptionPreset.PLAIN_JSON).with(module);

        SchemaGenerator generator = new SchemaGenerator(configBuilder.build());
        JsonNode jsonSchema = generator.generateSchema(Person.class);

        System.out.println(jsonSchema.toPrettyString());
    }

    static class Person {

        @NotNull UUID id;

        @NotNull String name;

        @NotNull @Email @Pattern(regexp = "\\b[A-Za-z0-9._%+-]+@baeldung\\.com\\b") String email;

        @NotNull String surname;

        @NotNull Address address;

        @Null String fullName;

        @NotNull Date createdAt;

        @Size(max = 10) List<Person> friends;

    }

    static class Address {

        @Null String street;

        @NotNull String city;

        @NotNull String country;
    }

}
