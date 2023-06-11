package com.baeldung.equalshashcoderecords;

import java.util.Objects;

record Movie(String name, Integer yearOfRelease, String distributor) {

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null) {
            return false;
        }
        if (!(other instanceof Movie)) {
            return false;
        }

        Movie movie = (Movie) other;
        if (movie.name.equals(this.name) && movie.yearOfRelease.equals(this.yearOfRelease)) {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, yearOfRelease);
    }
}
