module com.baeldung.logging {
    provides java.lang.System.LoggerFinder
      with com.baeldung.logging.CustomLoggerFinder;
    exports com.baeldung.logging;
}