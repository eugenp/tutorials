package com.baeldung.requesttimeout.domain;

import com.google.common.base.Stopwatch;
import org.springframework.data.jpa.repository.JpaRepository;

import static java.util.concurrent.TimeUnit.SECONDS;

public interface BookRepository extends JpaRepository<Book, String> {

    default void wasteTime() {
        Stopwatch watch = Stopwatch.createStarted();

        // delay for 2 seconds
        while (watch.elapsed(SECONDS) < 2) {
            int i = Integer.MIN_VALUE;
            while (i < Integer.MAX_VALUE) {
                i++;
            }
        }
    }
}
