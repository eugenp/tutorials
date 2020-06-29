package com.baeldung.ddd.hexagonal.adapters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.baeldung.ddd.hexagonal.domain.Alert;
import com.baeldung.ddd.hexagonal.domain.AlertId;
import com.baeldung.ddd.hexagonal.ports.AlertRepository;

public class InMemoryAlertRepository implements AlertRepository {

    private Map<AlertId, Alert> alertMap = new HashMap<>();

    @Override
    public void create(Alert alert) {
        alertMap.put(alert.getId(), alert);

    }

    @Override
    public Optional<Alert> retrieve(AlertId id) {
        return Optional.ofNullable(alertMap.get(id));
    }

    @Override
    public List<Alert> list() {
        return new ArrayList<>(alertMap.values());
    }

}
