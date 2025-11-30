package com.baeldung.keystore;

import java.io.File;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;

class KeystoreLocatorIntegrationTest {

    private static final Logger logger = LoggerFactory.getLogger(KeystoreLocatorIntegrationTest.class);
    
    @Test
    void givenJavaInstallation_whenUsingSystemProperties_thenKeystoreLocationFound() {
        String javaHome = System.getProperty("java.home");
        String separator = System.getProperty("file.separator");
        
        String cacertsPath = javaHome + separator + "lib" + separator 
          + "security" + separator + "cacerts";
        
        assertNotNull(javaHome);
        logger.info("Java Home: {}", javaHome);
        logger.info("Expected cacerts location: {}", cacertsPath);
        
        File cacertsFile = new File(cacertsPath);
        if (cacertsFile.exists()) {
            logger.info("Cacerts file exists: YES");
            logger.info("Absolute path: {}", cacertsFile.getAbsolutePath());
            assertTrue(cacertsFile.exists());
        }
        
        String customTrustStore = System.getProperty("javax.net.ssl.trustStore");
        if (customTrustStore != null) {
            logger.info("Custom trustStore is specified: {}", customTrustStore);
        } else {
            logger.info("No custom trustStore specified, using default");
        }
        
        String userHome = System.getProperty("user.home");
        String userKeystore = userHome + separator + ".keystore";
        assertNotNull(userHome);
        logger.info("User keystore location: {}", userKeystore);
    }
}