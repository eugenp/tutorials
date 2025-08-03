package com.baeldung.classtemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.ClassTemplate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ClassTemplate
@ExtendWith(GreeterClassTemplateInvocationContextProvider.class)
class GreeterClassTemplateTest {

    private final String language;

    GreeterClassTemplateTest(String language) {
        this.language = language;
    }

    @Test
    void whenGreet_thenLocalizedMessage() {

        Greeter greeter = new Greeter();
        String actual = greeter.greet("Baeldung", language);

        assertEquals(
          "it".equals(language) ? "Ciao Baeldung" : "Hello Baeldung",
          actual
        );
    }
}

