package com.baeldung.cdi.cdi2observers.tests;

import com.baeldung.cdi.cdi2observers.services.LowercaseTextService;
import com.baeldung.cdi.cdi2observers.services.TextService;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;

public class LowercaseTextServiceUnitTest {
    
    @Test
    public void givenLowercaseTextServiceInstance_whenCalledparseText_thenCorrect() {
        TextService textService = new LowercaseTextService();
        
        assertThat(textService.parseText("Baeldung")).isEqualTo("baeldung");
    }
}
