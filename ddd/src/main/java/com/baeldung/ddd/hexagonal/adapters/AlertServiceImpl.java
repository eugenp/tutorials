package com.baeldung.ddd.hexagonal.adapters;

import java.util.Optional;
import java.util.stream.Stream;

import com.baeldung.ddd.hexagonal.domain.Alert;
import com.baeldung.ddd.hexagonal.domain.AlertId;
import com.baeldung.ddd.hexagonal.domain.Severity;
import com.baeldung.ddd.hexagonal.ports.AlertRepository;
import com.baeldung.ddd.hexagonal.ports.AlertService;

public class AlertServiceImpl implements AlertService {

    private AlertRepository repository;

    public AlertServiceImpl(AlertRepository repository) {
        this.repository = repository;
    }

    @Override
    public Alert create(String summary, String description, Severity severity) {
        Alert alert = Alert.createAlert(summary, description, severity);
        repository.create(alert);
        return alert;
    }

    @Override
    public Optional<Alert> retrieve(AlertId id) {
        return repository.retrieve(id);
    }

    @Override
    public Stream<Alert> listAlerts() {
        return repository.list()
            .stream();
    }
}
