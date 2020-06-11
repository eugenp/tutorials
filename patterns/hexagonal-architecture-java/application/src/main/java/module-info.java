module com.baeldung.hexagonal.application {
    requires com.baeldung.hexagonal.domain;
    requires org.apache.commons.lang3;

    exports com.baeldung.hexagonal.application;
}
