module com.baeldung.logging.slf4j {
    requires org.slf4j;
    provides java.lang.System.LoggerFinder
      with com.baeldung.logging.slf4j.Slf4jLoggerFinder;
    exports com.baeldung.logging.slf4j;
}