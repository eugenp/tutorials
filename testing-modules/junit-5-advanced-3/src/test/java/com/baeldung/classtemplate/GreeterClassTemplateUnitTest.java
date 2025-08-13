package com.baeldung.classtemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static java.lang.System.Logger;
import static java.lang.System.Logger.Level;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.ClassTemplate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ClassTemplate
@ExtendWith(GreeterClassTemplateInvocationContextProvider.class)
class GreeterClassTemplateUnitTest {

    private static final Logger LOG =
        System.getLogger("GreeterClassTemplateUnitTest");

    private final String language;

    GreeterClassTemplateUnitTest(String language) {
        this.language = language;
    }

    @BeforeEach
    void logContext() {
        LOG.log(Level.INFO, () -> ">> Context: Language-" + language);
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

