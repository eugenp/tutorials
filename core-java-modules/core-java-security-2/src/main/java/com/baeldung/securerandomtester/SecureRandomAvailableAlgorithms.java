package com.baeldung.securerandomtester;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class SecureRandomAvailableAlgorithms {

    static String[] algorithmNames = { "NativePRNG", "NativePRNGBlocking", "NativePRNGNonBlocking", "PKCS11", "SHA1PRNG", "Windows-PRNG" };

    public static void main(String[] args) {
        for (int i = 0; i < algorithmNames.length; i++) {
            String name = algorithmNames[i];
            Boolean isAvailable = true;
            try {
                SecureRandom random = SecureRandom.getInstance(name);
            } catch (NoSuchAlgorithmException e) {
                isAvailable = false;
            }

            System.out.println("Algorithm " + name + (isAvailable ? " is" : " isn't") + " available");
        }
    }
}
