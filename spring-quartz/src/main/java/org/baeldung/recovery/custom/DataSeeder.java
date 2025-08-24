package org.baeldung.recovery.custom;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    private final ApplicationJobRepository repository;

    public DataSeeder(ApplicationJobRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) {
        if (repository.count() == 0) {
            ApplicationJob job = new ApplicationJob();
            job.setName("simpleJob");
            job.setEnabled(true);
            job.setCompleted(false);

            repository.save(job);
        }
    }
}
