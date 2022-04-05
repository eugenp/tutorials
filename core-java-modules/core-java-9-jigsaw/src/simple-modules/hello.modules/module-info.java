module hello.modules {
    exports com.baeldung.modules.hello;
    provides com.baeldung.modules.hello.HelloInterface with com.baeldung.modules.hello.HelloModules;
}