package com.baeldung.hexagonal;

import com.baeldung.hexagonal.adapter.*;
import com.baeldung.hexagonal.core.port.WeightTrackerRepository;
import com.baeldung.hexagonal.core.service.WeightTrackerService;

public class Client {
    public static void main(String[] args) {
        WeightTrackerRepository repository = RepoType.FILE_BASED.get(); // Use MEMORY_BASED TO store just in memory.
        AdapterType adapterType = AdapterType.CONSOLE_BASED; // Use SWING_BASED to display data in a Swing window.

        adapterType.create(repository).display();
    }

    private static enum RepoType {
        FILE_BASED(new FileBasedWeightTrackerRepositoryAdapter("/tmp/weight.data")),
        MEMORY_BASED(new InMemoryWeightTrackerRepositoryAdapter());

        private final WeightTrackerRepository repository;

        RepoType(WeightTrackerRepository repository) {
            this.repository = repository;
        }

        public WeightTrackerRepository get() {
            return repository;
        }
    }

    private static enum AdapterType {
        SWING_BASED {
            @Override
            public UIAdapter create(WeightTrackerRepository repository) {
                return new SwingHistoryAdapter(new WeightTrackerService(repository));
            }
        }, CONSOLE_BASED {
            public UIAdapter create(WeightTrackerRepository repository) {
                return ConsoleAdapter.of(new WeightTrackerService(repository));
            }
        };

        public abstract UIAdapter create(WeightTrackerRepository repository);
    }
}
