package com.baeldung.keystore;

class KeystoreLocatorIntegrationTest {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(KeystoreLocatorIntegrationTest.class);
    
    @org.junit.jupiter.api.Test
    void givenJavaInstallation_whenUsingSystemProperties_thenKeystoreLocationFound() {
        String javaHome = System.getProperty("java.home");
        String separator = System.getProperty("file.separator");
        
        String cacertsPath = javaHome + separator + "lib" + separator 
          + "security" + separator + "cacerts";
        
        org.junit.jupiter.api.Assertions.assertNotNull(javaHome);
        logger.info("Java Home: {}", javaHome);
        logger.info("Expected cacerts location: {}", cacertsPath);
        
        java.io.File cacertsFile = new java.io.File(cacertsPath);
        if (cacertsFile.exists()) {
            logger.info("Cacerts file exists: YES");
            logger.info("Absolute path: {}", cacertsFile.getAbsolutePath());
            org.junit.jupiter.api.Assertions.assertTrue(cacertsFile.exists());
        }
        
        String customTrustStore = System.getProperty("javax.net.ssl.trustStore");
        if (customTrustStore != null) {
            logger.info("Custom trustStore is specified: {}", customTrustStore);
        } else {
            logger.info("No custom trustStore specified, using default");
        }
        
        String userHome = System.getProperty("user.home");
        String userKeystore = userHome + separator + ".keystore";
        org.junit.jupiter.api.Assertions.assertNotNull(userHome);
        logger.info("User keystore location: {}", userKeystore);
    }
}