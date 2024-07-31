package com.baeldung.autovalue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class Person {

    public abstract String name();

    public abstract List<String> favoriteMovies();

    public static Builder builder() {
        return new AutoValue_Person.Builder();
    }

    @AutoValue.Builder
    public static abstract class Builder {

        public abstract Builder name(String value);

        public abstract Builder favoriteMovies(List<String> value);

        abstract List<String> favoriteMovies();

        abstract Person autoBuild();

        public Person build() {
            List<String> favoriteMovies = favoriteMovies();
            List<String> copy = Collections.unmodifiableList(new ArrayList<>(favoriteMovies));
            favoriteMovies(copy);
            return autoBuild();
        }
    }
}
