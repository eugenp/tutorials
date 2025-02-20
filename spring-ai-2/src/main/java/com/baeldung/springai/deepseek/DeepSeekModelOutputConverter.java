package com.baeldung.springai.deepseek;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.converter.StructuredOutputConverter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
class DeepSeekModelOutputConverter implements StructuredOutputConverter<DeepSeekModelResponse> {

    private final Logger logger = LoggerFactory.getLogger(DeepSeekModelOutputConverter.class);

    @Override
    public DeepSeekModelResponse convert(@NonNull String text) {
        String chainOfThought = "";
        String answer = text;

        int openingThinkTag = text.indexOf("<think>");
        int closingThinkTag = text.indexOf("</think>");

        if (openingThinkTag != -1 && closingThinkTag != -1 && closingThinkTag > openingThinkTag) {
            chainOfThought = text.substring(openingThinkTag + 7, closingThinkTag).trim();
            answer = text.substring(closingThinkTag + 8).trim();
        } else {
            logger.debug("No <think> tags found in the response. Treating entire text as answer.");
        }
        return new DeepSeekModelResponse(chainOfThought, answer);
    }

    @Override
    public String getFormat() {
        return "";
    }

}