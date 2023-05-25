package com.baeldung.mockito.java8;

import java.util.Optional;
import java.util.stream.Stream;

public class UnemploymentServiceImpl implements UnemploymentService {
    private final JobService jobService;
    
    public UnemploymentServiceImpl(JobService jobService) {
        this.jobService = jobService;
    }

    @Override
    public boolean personIsEntitledToUnemploymentSupport(Person person) {
        Optional<JobPosition> optional = jobService.findCurrentJobPosition(person);
        
        return !optional.isPresent();
    }

    @Override
    public Optional<JobPosition> searchJob(Person person, String searchString) {
        Stream<JobPosition> stream = jobService.listJobs(person);
        
        return stream.filter((j) -> j.getTitle().contains(searchString)).findFirst();
    }
}
