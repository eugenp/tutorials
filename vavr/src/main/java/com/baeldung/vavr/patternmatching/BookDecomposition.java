package com.baeldung.vavr.patternmatching;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.match.annotation.Patterns;
import io.vavr.match.annotation.Unapply;

@Patterns
public class BookDecomposition {

    @Unapply
    public static Tuple2<String, String> Book(Book book) {
        return Tuple.of(book.getCategory(), book.getName());
    }

}
