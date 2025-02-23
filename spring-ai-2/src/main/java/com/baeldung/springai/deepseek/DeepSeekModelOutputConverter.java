package com.baeldung.springai.deepseek;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.converter.StructuredOutputConverter;
import org.springframework.lang.NonNull;
import org.springframework.util.StringUtils;

class DeepSeekModelOutputConverter implements StructuredOutputConverter<DeepSeekModelResponse> {

    private static final Logger logger = LoggerFactory.getLogger(DeepSeekModelOutputConverter.class);
    private static final String OPENING_THINK_TAG = "<think>";
    private static final String CLOSING_THINK_TAG = "</think>";

    @Override
    public DeepSeekModelResponse convert(@NonNull String text) {
        if (!StringUtils.hasText(text)) {
            throw new IllegalArgumentException("Text cannot be blank");
        }
        int openingThinkTagIndex = text.indexOf(OPENING_THINK_TAG);
        int closingThinkTagIndex = text.indexOf(CLOSING_THINK_TAG);

        if (openingThinkTagIndex != -1 && closingThinkTagIndex != -1 && closingThinkTagIndex > openingThinkTagIndex) {
            String chainOfThought = text.substring(openingThinkTagIndex + OPENING_THINK_TAG.length(), closingThinkTagIndex);
            String answer = text.substring(closingThinkTagIndex + CLOSING_THINK_TAG.length());
            return new DeepSeekModelResponse(chainOfThought, answer);
        } else {
            logger.debug("No <think> tags found in the response. Treating entire text as answer.");
            return new DeepSeekModelResponse(null, text);
        }
    }

    /**
     * This method is used to define instructions for formatting the AI model's response,
     * which are appended to the user prompt.
     * See {@link org.springframework.ai.converter.BeanOutputConverter#getFormat()} for reference.
     *
     * However, in the current implementation, we extract only the AI response and its chain of thought,
     * so no formatting instructions are needed.
     */
    @Override
    public String getFormat() {
        return null;
    }

}