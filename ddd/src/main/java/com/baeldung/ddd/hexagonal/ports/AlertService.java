package com.baeldung.ddd.hexagonal.ports;

import java.util.Optional;
import java.util.stream.Stream;

import com.baeldung.ddd.hexagonal.domain.Alert;
import com.baeldung.ddd.hexagonal.domain.AlertId;
import com.baeldung.ddd.hexagonal.domain.Severity;

public interface AlertService {
    Alert create(final String summary, final String description, final Severity severity);

    Optional<Alert> retrieve(final AlertId id);

    Stream<Alert> listAlerts();
}
