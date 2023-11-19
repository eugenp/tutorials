package com.baeldung.libraries.flexypool;

import com.vladmihalcea.flexypool.FlexyPoolDataSource;
import com.vladmihalcea.flexypool.adaptor.HikariCPPoolAdapter;
import com.vladmihalcea.flexypool.config.Configuration;
import com.vladmihalcea.flexypool.connection.ConnectionDecoratorFactoryResolver;
import com.vladmihalcea.flexypool.event.ConnectionAcquireTimeThresholdExceededEvent;
import com.vladmihalcea.flexypool.event.ConnectionAcquireTimeoutEvent;
import com.vladmihalcea.flexypool.event.ConnectionLeaseTimeThresholdExceededEvent;
import com.vladmihalcea.flexypool.event.EventListener;
import com.vladmihalcea.flexypool.metric.micrometer.MicrometerMetrics;
import com.vladmihalcea.flexypool.strategy.IncrementPoolOnTimeoutConnectionAcquiringStrategy;
import com.vladmihalcea.flexypool.strategy.RetryConnectionAcquiringStrategy;
import com.vladmihalcea.flexypool.strategy.UniqueNamingStrategy;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@org.springframework.context.annotation.Configuration
public class FlexypoolConfig {

    @SuppressWarnings("unchecked")
    @Bean(initMethod = "start", destroyMethod = "stop")
    public FlexyPoolDataSource<HikariDataSource> flexypoolDataSource() {
        Configuration<HikariDataSource> configuration = flexypoolConfiguration();
        return new FlexyPoolDataSource<>(configuration, new IncrementPoolOnTimeoutConnectionAcquiringStrategy.Factory<>(5), new RetryConnectionAcquiringStrategy.Factory<>(2));
    }

    @Bean
    public Configuration<HikariDataSource> flexypoolConfiguration() {

        HikariDataSource dataSource = hikariDataSource();

        return new Configuration.Builder<>(UUID.randomUUID().toString(), dataSource, HikariCPPoolAdapter.FACTORY).setMetricsFactory(MicrometerMetrics::new)
          .setConnectionProxyFactory(ConnectionDecoratorFactoryResolver.INSTANCE.resolve())
          .setMetricLogReporterMillis(TimeUnit.SECONDS.toMillis(5))
          .setMetricNamingUniqueName(UniqueNamingStrategy.INSTANCE)
          .setJmxEnabled(true)
          .setJmxAutoStart(true)
          .setConnectionAcquireTimeThresholdMillis(50L)
          .setConnectionLeaseTimeThresholdMillis(250L)
          .setEventListenerResolver(() -> Arrays.asList(new ConnectionAcquireTimeoutEventListener(), new ConnectionAcquireTimeThresholdExceededEventListener(), new ConnectionLeaseTimeThresholdExceededEventListener()))
          .build();
    }

    @Bean
    public HikariDataSource hikariDataSource() {

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;INIT=runscript from 'classpath:/db.sql'");
        config.setUsername("");
        config.setPassword("");
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.addDataSourceProperty("minimumPoolSize", "1");
        config.addDataSourceProperty("maximumPoolSize", "3");
        config.addDataSourceProperty("connectionTimeout", "1000");
        return new HikariDataSource(config);
    }
}

class ConnectionAcquireTimeThresholdExceededEventListener extends EventListener<ConnectionAcquireTimeThresholdExceededEvent> {

    public static final Logger LOGGER = LoggerFactory.getLogger(ConnectionAcquireTimeThresholdExceededEventListener.class);

    public ConnectionAcquireTimeThresholdExceededEventListener() {
        super(ConnectionAcquireTimeThresholdExceededEvent.class);
    }

    @Override
    public void on(ConnectionAcquireTimeThresholdExceededEvent event) {
        LOGGER.info("ConnectionAcquireTimeThresholdExceededEvent Caught event {}", event);
    }
}

class ConnectionLeaseTimeThresholdExceededEventListener extends EventListener<ConnectionLeaseTimeThresholdExceededEvent> {

    public static final Logger LOGGER = LoggerFactory.getLogger(ConnectionLeaseTimeThresholdExceededEventListener.class);

    public ConnectionLeaseTimeThresholdExceededEventListener() {
        super(ConnectionLeaseTimeThresholdExceededEvent.class);
    }

    @Override
    public void on(ConnectionLeaseTimeThresholdExceededEvent event) {
        LOGGER.info("ConnectionLeaseTimeThresholdExceededEvent Caught event {}", event);
    }
}

class ConnectionAcquireTimeoutEventListener extends EventListener<ConnectionAcquireTimeoutEvent> {

    public static final Logger LOGGER = LoggerFactory.getLogger(ConnectionAcquireTimeoutEventListener.class);

    public ConnectionAcquireTimeoutEventListener() {
        super(ConnectionAcquireTimeoutEvent.class);
    }

    @Override
    public void on(ConnectionAcquireTimeoutEvent event) {
        LOGGER.info("ConnectionAcquireTimeoutEvent Caught event {}", event);
    }
}