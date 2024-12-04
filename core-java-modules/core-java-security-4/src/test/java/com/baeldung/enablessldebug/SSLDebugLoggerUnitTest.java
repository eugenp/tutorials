package com.baeldung.enablessldebug;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@Disabled // JAVA-42148
public class SSLDebugLoggerUnitTest {
    @Test
     void givenSSLDebuggingEnabled_whenUsingSystemProperties_thenEnableSSLDebugLogging() throws Exception {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setErr(new PrintStream(outContent));

        SSLDebugLogger.enableSSLDebugUsingSystemProperties();
        assertEquals("ssl", System.getProperty("javax.net.debug"));

        SSLDebugLogger.makeHttpsRequest();
        assertTrue(outContent.toString().contains("javax.net.ssl|DEBUG|"));
        outContent.reset();

        System.clearProperty("javax.net.debug");
        assertNull(System.getProperty("javax.net.debug"));

        SSLDebugLogger.makeHttpsRequest();
        assertEquals(outContent.toString(),"");
    }

    @Test
     void givenSSLDebuggingEnabled_whenUsingConfigurationFile_thenEnableSSLDebugLogging() throws IOException {
        InputStream configFile = SSLDebugLoggerUnitTest.class.getClassLoader().getResourceAsStream("logging.properties");
        LogManager.getLogManager().readConfiguration(configFile);

        Logger sslLogger = Logger.getLogger("javax.net.ssl");
        ConsoleHandler consoleHandler = (ConsoleHandler) sslLogger.getHandlers()[0];
        Level consoleHandlerLevel = consoleHandler.getLevel();

        assertEquals(Level.ALL, consoleHandlerLevel, "SSL ConsoleHandler level should be ALL");
    }
}
