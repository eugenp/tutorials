package com.baeldung.param;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.extension.ExtendWith;

@DisplayName("Testing PersonValidator")
public class PersonValidatorUnitTest {

    /**
     * Nested class, uses ExtendWith
     * {@link com.baeldung.param.ValidPersonParameterResolver ValidPersonParameterResolver}
     * to feed Test methods with "good" data.
     */
    @Nested
    @DisplayName("When using Valid data")
    @ExtendWith(ValidPersonParameterResolver.class)
    public class ValidData {

        /**
         * Repeat the test ten times, that way we have a good shot at
         * running all of the data through at least once.
         * 
         * @param person
         *          A valid Person object to validate.
         */
        @RepeatedTest(value = 10)
        @DisplayName("All first names are valid")
        public void validateFirstName(Person person) {
            try {
                assertTrue(PersonValidator.validateFirstName(person));
            } catch (PersonValidator.ValidationException e) {
                fail("Exception not expected: " + e.getLocalizedMessage());
            }
        }

        /**
         * Repeat the test ten times, that way we have a good shot at
         * running all of the data through at least once.
         * 
         * @param person
         *          A valid Person object to validate.
         */
        @RepeatedTest(value = 10)
        @DisplayName("All last names are valid")
        public void validateLastName(Person person) {
            try {
                assertTrue(PersonValidator.validateLastName(person));
            } catch (PersonValidator.ValidationException e) {
                fail("Exception not expected: " + e.getLocalizedMessage());
            }
        }

    }

    /**
     * Nested class, uses ExtendWith
     * {@link com.baeldung.param.InvalidPersonParameterResolver InvalidPersonParameterResolver}
     * to feed Test methods with "bad" data.
     */
    @Nested
    @DisplayName("When using Invalid data")
    @ExtendWith(InvalidPersonParameterResolver.class)
    public class InvalidData {

        /**
         * Repeat the test ten times, that way we have a good shot at
         * running all of the data through at least once.
         * 
         * @param person
         *          An invalid Person object to validate.
         */
        @RepeatedTest(value = 10)
        @DisplayName("All first names are invalid")
        public void validateFirstName(Person person) {
            assertThrows(PersonValidator.ValidationException.class, () -> PersonValidator.validateFirstName(person));
        }

        /**
         * Repeat the test ten times, that way we have a good shot at
         * running all of the data through at least once.
         * 
         * @param person
         *          An invalid Person object to validate.
         */
        @RepeatedTest(value = 10)
        @DisplayName("All first names are invalid")
        public void validateLastName(Person person) {
            assertThrows(PersonValidator.ValidationException.class, () -> PersonValidator.validateLastName(person));
        }

    }

}
