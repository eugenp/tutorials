package com.baeldung.keystore;

import org.junit.jupiter.api.Test;
import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class KeystoreLocatorIntegrationTest {
    
    @Test
    void givenJavaInstallation_whenUsingSystemProperties_thenKeystoreLocationFound() {
        String javaHome = System.getProperty("java.home");
        String separator = System.getProperty("file.separator");
        
        String cacertsPath = javaHome + separator + "lib" + separator 
          + "security" + separator + "cacerts";
        
        assertNotNull(javaHome);
        System.out.println("Java Home: " + javaHome);
        System.out.println("Expected cacerts location: " + cacertsPath);
        
        File cacertsFile = new File(cacertsPath);
        if (cacertsFile.exists()) {
            System.out.println("Cacerts file exists: YES");
            System.out.println("Absolute path: " + cacertsFile.getAbsolutePath());
            assertTrue(cacertsFile.exists());
        }
        
        String customTrustStore = System.getProperty("javax.net.ssl.trustStore");
        if (customTrustStore != null) {
            System.out.println("Custom trustStore is specified: " + customTrustStore);
        } else {
            System.out.println("No custom trustStore specified, using default");
        }
        
        String userHome = System.getProperty("user.home");
        String userKeystore = userHome + separator + ".keystore";
        assertNotNull(userHome);
        System.out.println("User keystore location: " + userKeystore);
    }
}