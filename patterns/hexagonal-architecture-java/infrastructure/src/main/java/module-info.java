module com.baeldung.hexagonal.infrastructure {
    requires java.sql;
    requires org.slf4j;
    requires lombok;
    requires com.baeldung.hexagonal.application;
    requires com.baeldung.hexagonal.domain;
    exports com.baeldung.hexagonal.infrastructure;
}
