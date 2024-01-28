package com.baeldung.spock.data;
public class DataPipesSubject {
    long addWithATwist(final long first, final long second) {
        if (first == 42 || second == 42) {
            return 42;
        }
        return first + second;
    }

    String addExclamation(final String first) {
        return first + '!';
    }
}
