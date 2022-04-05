package com.baeldung.egd;

import java.security.SecureRandom;

/**
 * JavaSecurityEgdTester - run this with JVM parameter java.security.egd, e.g.:
 *     java -Djava.security.egd=file:/dev/urandom -cp . com.baeldung.egd.JavaSecurityEgdTester
 */
public class JavaSecurityEgdTester {
    public static final double NANOSECS = 1000000000.0;
    public static final String JAVA_SECURITY_EGD = "java.security.egd";

    public static void main(String[] args) {
        SecureRandom secureRandom = new SecureRandom();
        long start = System.nanoTime();
        byte[] randomBytes = new byte[256];
        secureRandom.nextBytes(randomBytes);
        double duration = (System.nanoTime() - start) / NANOSECS;

        String message = String.format("java.security.egd=%s took %.3f seconds and used the %s algorithm", System.getProperty(JAVA_SECURITY_EGD), duration, secureRandom.getAlgorithm());
        System.out.println(message);
    }
}
