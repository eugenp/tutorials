package com.baeldung.hexagonal.core.service;

import com.baeldung.hexagonal.core.model.WeightInTime;
import com.baeldung.hexagonal.core.port.WeightTrackerAllService;
import com.baeldung.hexagonal.core.port.WeightTrackerEditorService;
import com.baeldung.hexagonal.core.port.WeightTrackerRepository;
import com.baeldung.hexagonal.core.port.WeightTrackerViewerService;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public class WeightTrackerService implements WeightTrackerEditorService, WeightTrackerViewerService, WeightTrackerAllService {
    private final WeightTrackerRepository repository;

    public WeightTrackerService(WeightTrackerRepository repository) {
        this.repository = repository;
    }

    @Override
    public void addWeight(float weightInKG, LocalDateTime date) {
        repository.addWeight(weightInKG, date);
        System.out.printf(">> Saving the weight %s in %s%n", weightInKG, date);
    }

    @Override
    public List<WeightInTime> getWeightHistory() {
        return Collections.unmodifiableList(repository.getWeightHistory());
    }

    @Override
    public boolean remove(long id) {
        return repository.deleteById(id);
    }
}
