package com.baeldung.displayname;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.lang.reflect.Method;

@DisplayNameGeneration(DisplayNameGeneratorUnitTest.ReplaceCamelCase.class)
class DisplayNameGeneratorUnitTest {

    @Test
    void camelCaseName() {
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGeneratorUnitTest.IndicativeSentences.class)
    class ANumberIsFizz {
        @Test
        void ifItIsDivisibleByThree() {
        }

        @ParameterizedTest(name = "Number {0} is fizz.")
        @ValueSource(ints = { 3, 12, 18 })
        void ifItIsOneOfTheFollowingNumbers(int number) {
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGeneratorUnitTest.IndicativeSentences.class)
    class ANumberIsBuzz {
        @Test
        void ifItIsDivisibleByFive() {
        }

        @ParameterizedTest(name = "Number {0} is buzz.")
        @ValueSource(ints = { 5, 10, 20 })
        void ifItIsOneOfTheFollowingNumbers(int number) {
        }
    }

    static class IndicativeSentences extends ReplaceCamelCase {
        @Override
        public String generateDisplayNameForNestedClass(Class<?> nestedClass) {
            return super.generateDisplayNameForNestedClass(nestedClass) + "...";
        }

        @Override
        public String generateDisplayNameForMethod(Class<?> testClass, Method testMethod) {
            return replaceCamelCase(testClass.getSimpleName() + " " + testMethod.getName()) + ".";
        }
    }

    static class ReplaceCamelCase extends DisplayNameGenerator.Standard {
        @Override
        public String generateDisplayNameForClass(Class<?> testClass) {
            return replaceCamelCase(super.generateDisplayNameForClass(testClass));
        }

        @Override
        public String generateDisplayNameForNestedClass(Class<?> nestedClass) {
            return replaceCamelCase(super.generateDisplayNameForNestedClass(nestedClass));
        }

        @Override
        public String generateDisplayNameForMethod(Class<?> testClass, Method testMethod) {
            return this.replaceCamelCase(testMethod.getName()) + DisplayNameGenerator.parameterTypesAsString(testMethod);
        }

        String replaceCamelCase(String camelCase) {
            StringBuilder result = new StringBuilder();
            result.append(camelCase.charAt(0));
            for (int i=1; i<camelCase.length(); i++) {
                if (Character.isUpperCase(camelCase.charAt(i))) {
                    result.append(' ');
                    result.append(Character.toLowerCase(camelCase.charAt(i)));
                } else {
                    result.append(camelCase.charAt(i));
                }
            }
            return result.toString();
        }
    }
}
