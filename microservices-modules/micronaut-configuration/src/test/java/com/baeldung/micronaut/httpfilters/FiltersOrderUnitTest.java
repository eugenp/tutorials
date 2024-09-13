package com.baeldung.micronaut.httpfilters;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import com.baeldung.micronaut.httpfilters.filters.CustomFilter;
import com.baeldung.micronaut.httpfilters.filters.PrivilegedUsersEndpointFilter;
import com.baeldung.micronaut.httpfilters.filters.RequestIDFilter;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import io.restassured.specification.RequestSpecification;

@MicronautTest
class FiltersOrderUnitTest {

    private ListAppender<ILoggingEvent> customFilterLogs;
    private ListAppender<ILoggingEvent> requestIDFilterLogs;
    private ListAppender<ILoggingEvent> privilegedUsersEndpointFilterLogs;

    @BeforeEach
    void setUp() {
        customFilterLogs = getListAppenderForClass(CustomFilter.class);
        requestIDFilterLogs = getListAppenderForClass(RequestIDFilter.class);
        privilegedUsersEndpointFilterLogs = getListAppenderForClass(PrivilegedUsersEndpointFilter.class);
    }

    @Test
    public void givenFilterOrder_whenRequestIsFilteredByAllFilters_thenTheOrderIsRight(RequestSpecification spec) {
        spec.given()
            .basePath("micronaut-configuration-tutorials/filters-annotations")
            .when()
            .get("/endpoint1");

        Optional<ILoggingEvent> customFilterLog = customFilterLogs.list.stream()
            .filter(e -> e.getMessage()
                .contains("request header:"))
            .findFirst();
        Optional<ILoggingEvent> requestIdFilterLog = requestIDFilterLogs.list.stream()
            .filter(e -> e.getMessage()
                .contains("request ID"))
            .findFirst();
        Optional<ILoggingEvent> privilegedUserFilterLog = privilegedUsersEndpointFilterLogs.list.stream()
            .filter(e -> e.getMessage()
                .contains("Privileged user was filtered"))
            .findFirst();

        assertThat(customFilterLog).isPresent();
        assertThat(requestIdFilterLog).isPresent();
        assertThat(privilegedUserFilterLog).isPresent();
        assertThat(customFilterLog.get()
            .getNanoseconds()).isGreaterThan(requestIdFilterLog.get()
            .getNanoseconds());
        assertThat(privilegedUserFilterLog.get()
            .getNanoseconds()).isGreaterThan(customFilterLog.get()
            .getNanoseconds());
    }

    @Test
    public void givenFilterOrder_whenRequestIsFilteredBy2Filters_thenTheOrderIsRight(RequestSpecification spec) {
        spec.given()
            .basePath("micronaut-configuration-tutorials/filters-annotations")
            .when()
            .get("/endpoint2");

        Optional<ILoggingEvent> customFilterLog = customFilterLogs.list.stream()
            .filter(e -> e.getMessage()
                .contains("request header:"))
            .findFirst();
        Optional<ILoggingEvent> requestIdFilterLog = requestIDFilterLogs.list.stream()
            .filter(e -> e.getMessage()
                .contains("request ID"))
            .findFirst();
        Optional<ILoggingEvent> privilegedUserFilterLog = privilegedUsersEndpointFilterLogs.list.stream()
            .filter(e -> e.getMessage()
                .contains("Privileged user was filtered"))
            .findFirst();

        assertThat(customFilterLog).isPresent();
        assertThat(requestIdFilterLog).isPresent();
        assertThat(privilegedUserFilterLog).isEmpty();
        assertThat(customFilterLog.get()
            .getNanoseconds()).isGreaterThan(requestIdFilterLog.get()
            .getNanoseconds());
    }

    private static ListAppender<ILoggingEvent> getListAppenderForClass(Class<?> clazz) {
        Logger logger = (Logger) LoggerFactory.getLogger(clazz);
        ListAppender<ILoggingEvent> loggingEventListAppender = new ListAppender<>();
        loggingEventListAppender.start();
        logger.addAppender(loggingEventListAppender);

        return loggingEventListAppender;
    }
}
