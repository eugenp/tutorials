module core.java {
    requires jdk.incubator.vector;
    requires jdk.incubator.foreign;
    requires jmh.core;
    requires jdk.unsupported;
    exports com.baeldung.randomgenerators.jmh_generated;
}