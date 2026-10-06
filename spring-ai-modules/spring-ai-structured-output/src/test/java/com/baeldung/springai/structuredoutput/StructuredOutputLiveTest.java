package com.baeldung.springai.structuredoutput;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.converter.ListOutputConverter;
import org.springframework.ai.converter.MapOutputConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;

import java.util.List;
import java.util.Map;

@SpringBootTest
@EnabledIfEnvironmentVariable(named = "OPENAI_API_KEY", matches = ".*")
class StructuredOutputLiveTest {

    @Autowired
    private ChatClient chatClient;

    @Autowired
    private ChatModel chatModel;

    @Test
    void whenUsingBeanOutputConverter_thenResponseIsConvertedToDomainEntity() {
        Recipe recipe = chatClient
            .prompt("Generate a recipe for a vegetarian lasagna.")
            .call()
            .entity(Recipe.class);

        assertThat(recipe)
            .hasNoNullFieldsOrProperties()
            .satisfies(r -> assertThat(r.ingredients())
                .hasSizeGreaterThan(1)
            );
    }

    @Test
    void whenUsingParameterizedTypeReference_thenResponseIsConvertedToListOfDomainEntities() {
        List<Recipe> recipes = chatClient
            .prompt("Generate 3 recipes for vegetarian dishes.")
            .call()
            .entity(new ParameterizedTypeReference<List<Recipe>>() {});

        assertThat(recipes)
            .hasSize(3)
            .allSatisfy(recipe -> assertThat(recipe)
                .hasNoNullFieldsOrProperties()
            );
    }

    @Test
    void whenUsingListOutputConverter_thenResponseIsConvertedToListOfStrings() {
        List<String> dishes = chatClient
            .prompt("List 5 popular vegetarian dishes.")
            .call()
            .entity(new ListOutputConverter());

        assertThat(dishes)
            .hasSize(5)
            .allSatisfy(dish -> assertThat(dish)
                .isNotBlank()
            );
    }

    @Test
    void whenUsingMapOutputConverter_thenResponseIsConvertedToMap() {
        Map<String, Object> nutritionFacts = chatClient
            .prompt("Provide the nutrition facts per serving for a vegetarian lasagna.")
            .call()
            .entity(new MapOutputConverter());

        assertThat(nutritionFacts)
            .isNotEmpty()
            .allSatisfy((nutrient, value) -> {
                assertThat(nutrient).isNotBlank();
                assertThat(value).isNotNull();
            });
    }

    @Test
    void givenChatModel_whenUsingBeanOutputConverter_thenResponseIsConvertedToDomainEntity() {
        BeanOutputConverter<Recipe> outputConverter = new BeanOutputConverter<>(Recipe.class);

        String response = chatModel
            .call("Generate a recipe for a vegetarian lasagna. " + outputConverter.getFormat());
        Recipe recipe = outputConverter.convert(response);

        assertThat(recipe)
            .hasNoNullFieldsOrProperties();
    }

    @Test
    void whenPerformingClientSideSchemaValidation_thenResponseIsConvertedToDomainEntity() {
        Recipe recipe = chatClient
            .prompt("Generate a recipe for a high protein dessert.")
            .call()
            .entity(Recipe.class, spec -> spec.validateSchema());

        assertThat(recipe)
            .hasNoNullFieldsOrProperties();
    }

    @Test
    void whenUsingProviderStructuredOutput_thenResponseIsConvertedToDomainEntity() {
        Recipe recipe = chatClient
            .prompt("Generate a recipe for a gluten-free breakfast.")
            .call()
            .entity(Recipe.class, spec -> spec.useProviderStructuredOutput());

        assertThat(recipe)
            .hasNoNullFieldsOrProperties();
    }

    @Test
    void whenUsingCustomYamlOutputConverter_thenResponseIsConvertedToDomainEntity() {
        Recipe recipe = chatClient
            .prompt("Generate a recipe for a healthy veggie salad.")
            .call()
            .entity(new YamlOutputConverter<>(Recipe.class));

        assertThat(recipe)
            .hasNoNullFieldsOrProperties();
    }
}