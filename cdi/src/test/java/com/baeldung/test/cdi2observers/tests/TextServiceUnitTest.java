package com.baeldung.cdi.cdi2observers.tests;

import com.baeldung.cdi.cdi2observers.services.TextService;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;

public class TextServiceUnitTest {

    @Test
    public void givenTextServiceInstance_whenCalledparseText_thenCorrect() {
        TextService textService = new TextService();
        assertThat(textService.parseText("Baeldung")).isEqualTo("BAELDUNG");
    }
}
