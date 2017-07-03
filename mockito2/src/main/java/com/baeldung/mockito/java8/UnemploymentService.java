package com.baeldung.mockito.java8;

import java.util.Optional;

interface UnemploymentService {

    boolean personIsEntitledToUnemploymentSupport(Person person);

    Optional<JobPosition> searchJob(Person person, String searchString); 

}
