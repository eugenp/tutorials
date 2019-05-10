package com.baeldung.hexagonal;

import com.baeldung.hexagonal.notification.NotifierPort;
import com.baeldung.hexagonal.repository.RepositoryPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class WeatherAlertManager {

    private static final Logger LOG = LoggerFactory.getLogger(WeatherAlertManager.class);

    private List<NotifierPort> notifiers;

    private List<RepositoryPort> repositories;

    public void process(WeatherAlert weatherAlert) {
        LOG.info("Processing weather alert [{}]...", weatherAlert);
        this.getRepositories().stream().forEach(r -> r.save(weatherAlert));
        this.getNotifiers().stream().forEach(n -> n.send(weatherAlert));
        LOG.info("Processed weather alert [{}]", weatherAlert);
    }

    public void addNotifier(NotifierPort notifier) {
        getNotifiers().add(notifier);
    }

    public void addRepository(RepositoryPort repository) {
        getRepositories().add(repository);
    }

    public List<NotifierPort> getNotifiers() {
        if (notifiers == null) {
            notifiers = new ArrayList<>();
        }
        return notifiers;
    }

    public List<RepositoryPort> getRepositories() {
        if (repositories == null) {
            repositories = new ArrayList<>();
        }
        return repositories;
    }

}
