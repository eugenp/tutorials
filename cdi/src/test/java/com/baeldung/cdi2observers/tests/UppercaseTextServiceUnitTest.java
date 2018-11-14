package com.baeldung.cdi.cdi2observers.tests;

import com.baeldung.cdi.cdi2observers.services.TextService;
import com.baeldung.cdi.cdi2observers.services.UppercaseTextService;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;

public class UppercaseTextServiceUnitTest {

    @Test
    public void givenLowercaseTextServiceInstance_whenCalledparseText_thenCorrect() {
        TextService textService = new UppercaseTextService();
        
        assertThat(textService.parseText("Baeldung")).isEqualTo("BAELDUNG");
    }
}
