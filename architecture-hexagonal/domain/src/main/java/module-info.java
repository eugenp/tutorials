module com.baeldung.domain {
    requires spring.data.jpa;
    requires spring.data.commons;
    requires spring.context;
    requires persistence.api;
    requires spring.core;
    requires spring.beans;
    exports com.baeldung.boot.architecture.hexagonal.domain;
    exports com.baeldung.boot.architecture.hexagonal.repository;

}