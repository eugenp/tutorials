package com.baeldung.imagegen;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(
    classes = Application.class, 
    properties = "spring.ai.openai.image.options.response-format=b64_json"
)
class ImageGeneratorLiveTest {

    @Autowired
    private ImageGenerator imageGenerator;

    private static final String PROMPT = "a banana shaped watermelon training in the gym";

    @Test
    void whenGeneratingImageFromPrompt_thenBase64StringResponseReturned() {
        String response = imageGenerator.generate(PROMPT);

        assertThat(response)
            .isNotBlank()
            .isBase64();
    }

}