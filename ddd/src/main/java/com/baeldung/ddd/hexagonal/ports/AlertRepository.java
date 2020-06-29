package com.baeldung.ddd.hexagonal.ports;

import java.util.List;
import java.util.Optional;

import com.baeldung.ddd.hexagonal.domain.Alert;
import com.baeldung.ddd.hexagonal.domain.AlertId;

public interface AlertRepository {
    void create(Alert alert);

    Optional<Alert> retrieve(AlertId id);

    List<Alert> list();
}
