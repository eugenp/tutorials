package com.baeldung.hexagonal;

import com.baeldung.hexagonal.administration.AdministrationManager;
import com.baeldung.hexagonal.administration.TestAdministrationAdapter;
import com.baeldung.hexagonal.datafeed.DataFeeder;
import com.baeldung.hexagonal.datafeed.HttpDataFeedAdapter;
import com.baeldung.hexagonal.datafeed.TestDataFeedAdapter;
import com.baeldung.hexagonal.notification.LoggingNotifier;
import com.baeldung.hexagonal.notification.NotifierPort;
import com.baeldung.hexagonal.repository.DatabaseRepository;
import com.baeldung.hexagonal.repository.InMemoryRepository;
import com.baeldung.hexagonal.repository.RepositoryPort;

import java.util.List;

public class Application {

    private TestAdministrationAdapter testAdministrationAdapter;

    private TestDataFeedAdapter testDataFeedAdapter;

    private HttpDataFeedAdapter httpDataFeedAdapter;

    private WeatherAlertManager weatherAlertManager;

    public Application() {

        this.weatherAlertManager = new WeatherAlertManager();

        // Primary actors - they drive the application
        AdministrationManager administrationManager = new AdministrationManager(weatherAlertManager);
        this.testAdministrationAdapter = new TestAdministrationAdapter(administrationManager);

        DataFeeder dataFeeder = new DataFeeder(weatherAlertManager);
        this.testDataFeedAdapter = new TestDataFeedAdapter(dataFeeder);
        this.httpDataFeedAdapter = new HttpDataFeedAdapter(dataFeeder);

        // Secondary actors - are driven by the application
        LoggingNotifier loggingNotifier = new LoggingNotifier();
        weatherAlertManager.addNotifier(loggingNotifier);

        InMemoryRepository inMemoryRepository = new InMemoryRepository();
        DatabaseRepository databaseRepository = new DatabaseRepository();
        weatherAlertManager.addRepository(inMemoryRepository);
        weatherAlertManager.addRepository(databaseRepository);
    }

    public TestAdministrationAdapter getTestAdministrationAdapter() {
        return testAdministrationAdapter;
    }

    public TestDataFeedAdapter getTestDataFeedAdapter() {
        return testDataFeedAdapter;
    }

    public HttpDataFeedAdapter getHttpDataFeedAdapter() {
        return httpDataFeedAdapter;
    }

    public List<NotifierPort> getNotifiers() {
        return this.weatherAlertManager.getNotifiers();
    }

    public List<RepositoryPort> getRepositories() {
        return this.weatherAlertManager.getRepositories();
    }
}
