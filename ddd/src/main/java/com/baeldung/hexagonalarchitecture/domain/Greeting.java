package com.baeldung.hexagonalarchitecture.domain;

import java.util.Arrays;
import java.util.Objects;

public class Greeting {
        private String[] greetings;

        public Greeting(String greeting) {
                Objects.requireNonNull(greeting);
                this.greetings = greeting.split("\\r?\\n");
        }

        public String[] getGreetings() {
                return greetings;
        }

        @Override public String toString() {
                return Arrays.toString(greetings);
        }

        @Override public boolean equals(Object o) {
                if (this == o) {
                        return true;
                }
                if (o == null || getClass() != o.getClass()) {
                        return false;
                }
                Greeting greeting = (Greeting) o;
                return Arrays.equals(greetings, greeting.greetings);

        }

        @Override public int hashCode() {
                return Arrays.hashCode(greetings);
        }
}
