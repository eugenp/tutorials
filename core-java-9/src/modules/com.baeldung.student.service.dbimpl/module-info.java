module com.baeldung.student.service.dbimpl{
    requires transitive com.baeldung.student.service;
    exports com.baeldung.student.service.dbimpl;
    requires java.logging;
}