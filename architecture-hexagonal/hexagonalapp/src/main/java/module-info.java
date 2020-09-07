module com.baeldung.hexagonalapp {
    requires spring.boot.autoconfigure;
    requires spring.boot;
    requires spring.beans;
    requires com.baeldung.domain;
    opens com.baeldung.boot.architecture.hexagonal.app to spring.core;
    exports com.baeldung.boot.architecture.hexagonal.app to spring.beans, spring.context;
}