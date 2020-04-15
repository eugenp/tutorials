package com.baeldung.test.cdi2observers.tests;

import com.baeldung.cdi2observers.services.TextService;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TextServiceUnitTest {

    @Test
    public void givenTextServiceInstance_whenCalledparseText_thenCorrect() {
        TextService textService = new TextService();
        assertThat(textService.parseText("Baeldung")).isEqualTo("BAELDUNG");
    }
}
