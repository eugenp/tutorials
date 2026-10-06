package com.baeldung.springai.structuredoutput;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class YamlOutputConverterUnitTest {

    @Test
    void whenConvertingYamlResponse_thenResponseIsConvertedToDomainEntity() {
        String yamlResponse = """
            name: "Mediterranean Veggie Salad"
            cuisine: "Mediterranean"
            difficulty: "EASY"
            prepTimeMinutes: 15
            ingredients:
              - name: "Cucumber"
                quantity: "1 medium"
              - name: "Cherry tomatoes"
                quantity: "1 cup"
              - name: "Extra virgin olive oil"
                quantity: "2 tbsp"
            steps:
              - "Step 1: Chop the cucumber and halve the cherry tomatoes."
              - "Step 2: Drizzle with olive oil and toss everything together."
            """;

        Recipe recipe = new YamlOutputConverter<>(Recipe.class)
            .convert(yamlResponse);

        assertThat(recipe)
            .hasNoNullFieldsOrProperties();
    }
}