package com.baeldung.enablessldebug;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.junit.Test;

public class SSLDebugLoggerUnitTest {
    
    @Test
    public void givenSSLDebuggingEnabled_whenUsingSystemProperties_thenEnableSSL() {
        SSLDebugLogger.enableSSLDebugUsingSystemProperties();
        
        assertEquals("ssl:debug", System.getProperty("javax.net.debug"));
    }

    @Test
    public void givenSSLDebuggingEnabled_whenUsingConfigurationFile_thenEnableSSL() throws IOException {
        InputStream configFile = SSLDebugLoggerUnitTest.class.getClassLoader().getResourceAsStream("logging.properties");
        LogManager.getLogManager().readConfiguration(configFile);

        Logger sslLogger = Logger.getLogger("javax.net.ssl");
        ConsoleHandler consoleHandler = (ConsoleHandler) sslLogger.getHandlers()[0];
        Level consoleHandlerLevel = consoleHandler.getLevel();
        
        assertEquals(Level.ALL, consoleHandlerLevel, "SSL ConsoleHandler level should be ALL");
    }
}
