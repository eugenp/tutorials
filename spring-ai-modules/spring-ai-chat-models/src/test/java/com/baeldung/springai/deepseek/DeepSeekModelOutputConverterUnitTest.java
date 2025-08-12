package com.baeldung.springai.deepseek;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import net.bytebuddy.utility.RandomString;

class DeepSeekModelOutputConverterUnitTest {

    private final DeepSeekModelOutputConverter deepSeekModelOutputConverter = new DeepSeekModelOutputConverter();

    @Test
    void whenResponseContainsThinkTags_thenConverterExtractsCoTAndAnswer() {
        String chainOfThought = RandomString.make();
        String answer = RandomString.make();
        String aiResponse = "<think>%s</think>%s".formatted(chainOfThought, answer);

        DeepSeekModelResponse deepSeekModelResponse = deepSeekModelOutputConverter.convert(aiResponse);

        assertThat(deepSeekModelResponse)
            .isNotNull();
        assertThat(deepSeekModelResponse.chainOfThought())
            .isEqualTo(chainOfThought);
        assertThat(deepSeekModelResponse.answer())
            .isEqualTo(answer);
    }

    @Test
    void whenResponseHasNoThinkTags_thenConverterTreatsEntireTextAsAnswer() {
        String aiResponse = RandomString.make();

        DeepSeekModelResponse deepSeekModelResponse = deepSeekModelOutputConverter.convert(aiResponse);

        assertThat(deepSeekModelResponse)
            .isNotNull();
        assertThat(deepSeekModelResponse.chainOfThought())
            .isNull();
        assertThat(deepSeekModelResponse.answer())
            .isEqualTo(aiResponse);
    }

    @ParameterizedTest
    @MethodSource("blankStringAndNullInputSource")
    void whenResponseIsBlankOrNull_thenConverterThrowsIllegalArgumentException(String aiResponse) {
        assertThatThrownBy(() -> deepSeekModelOutputConverter.convert(aiResponse))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Text cannot be blank");
    }

    private static String[] blankStringAndNullInputSource() {
        return new String[] { null, "" };
    }

}