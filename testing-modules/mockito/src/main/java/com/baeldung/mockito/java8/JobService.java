package com.baeldung.mockito.java8;

import java.util.Optional;
import java.util.stream.Stream;

public interface JobService {
    Optional<JobPosition> findCurrentJobPosition(Person person);

    default boolean assignJobPosition(Person person, JobPosition jobPosition) {
        if (!findCurrentJobPosition(person).isPresent()) {
            person.setCurrentJobPosition(jobPosition);

            return true;
        } else {
            return false;
        }
    }

    Stream<JobPosition> listJobs(Person person);
}
