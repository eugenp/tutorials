module com.baeldung.library.test {
    requires com.baeldung.library.core;
    requires org.junit.jupiter.api;
    opens com.baeldung.library.test to org.junit.platform.commons;
}