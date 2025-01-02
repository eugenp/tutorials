package com.baeldung.displayname;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ReplaceUnderscoresGeneratorUnitTest {

    @Nested
    class when_doing_something {

        @Test
        void then_something_should_happen() {
        }

        @Test
        @DisplayName("@DisplayName takes precedence over generation")
        void override_generator() {
        }
    }
}
