module com.baeldung.hexagonal.domain {
    requires lombok;
    exports com.baeldung.hexagonal.domain.data;
    exports com.baeldung.hexagonal.domain.repo;
    exports com.baeldung.hexagonal.domain.services;
}
