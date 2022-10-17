package com.baeldung.namedformatting;

import org.apache.commons.text.StrSubstitutor;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class NamedFormatterUnitTest {
    private static final String TEMPLATE = "Text: [${text}] Number: [${number}] Text again: [${text}]";

    @Test
    void givenTemplateWithNamedParam_whenCallingCommonsTextStrSubstitutor_shouldGetExpectedResult() {
        Map<String, Object> params = new HashMap<>();
        params.put("text", "It's awesome!");
        params.put("number", 42);
        String result = StrSubstitutor.replace(TEMPLATE, params, "${", "}");
        assertThat(result).isEqualTo("Text: [It's awesome!] Number: [42] Text again: [It's awesome!]");
    }

    @Test
    void givenTemplateWithNamedParam_whenCallingCommonsTextStrSubstitutorWithParameterNames_shouldNotWorkAsExpected() {
        Map<String, Object> params = new HashMap<>();
        params.put("text", "'${number}' is a placeholder.");
        params.put("number", 42);
        String result = StrSubstitutor.replace(TEMPLATE, params, "${", "}");

        assertThat(result).isNotEqualTo("Text: ['${number}' is a placeholder.] Number: [42] Text again: ['${number}' is a placeholder.]");

        assertThat(result).isEqualTo("Text: ['42' is a placeholder.] Number: [42] Text again: ['42' is a placeholder.]");
    }

    @Test
    void givenTemplateWithNamedParam_whenCallingNamedFormatter_shouldGetExpectedResult() {
        Map<String, Object> params = new HashMap<>();
        params.put("text", "It's awesome!");
        params.put("number", 42);
        String result = NamedFormatter.format(TEMPLATE, params);
        assertThat(result).isEqualTo("Text: [It's awesome!] Number: [42] Text again: [It's awesome!]");

        params.put("text", "'${number}' is a placeholder.");
        result = NamedFormatter.format(TEMPLATE, params);
        assertThat(result).isEqualTo("Text: ['${number}' is a placeholder.] Number: [42] Text again: ['${number}' is a placeholder.]");
    }
}
