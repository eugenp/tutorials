package com.baeldung.kdf;

import javax.crypto.KDF;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.HKDFParameterSpec;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.Mac;

import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.InvalidKeyException;

/**
 * Examples of the Key Derivation Function (KDF) API introduced in Java 25.
 */
public class KdfApiJava25 {

    public void demonstrateArchitecture() throws NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        // Entry point: Creating a KDF instance
        KDF kdf = KDF.getInstance("HKDF-SHA256");

        // Mock parameters for demonstration
        HKDFParameterSpec paramSpec = HKDFParameterSpec.ofExtract()
          .addIKM(new byte[32])
          .thenExpand(new byte[16], 32);

        // Derive a typed SecretKey
        SecretKey key = kdf.deriveKey("AES", paramSpec);

        // Derive raw byte material
        byte[] rawKeyMaterial = kdf.deriveData(paramSpec);
    }

    public void demonstrateDerivationMethods(byte[] ikm, byte[] salt, byte[] info, GCMParameterSpec gcmSpec) throws Exception {

        KDF kdf = KDF.getInstance("HKDF-SHA256");
        HKDFParameterSpec hkdfParams = HKDFParameterSpec.ofExtract()
          .addIKM(ikm)
          .addSalt(salt)
          .thenExpand(info, 32);

        // Using deriveKey for immediately usable JCA objects
        SecretKey aesKey = kdf.deriveKey("AES", hkdfParams);
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        cipher.init(Cipher.ENCRYPT_MODE, aesKey, gcmSpec);

        // Using deriveData for raw octets/custom protocols
        byte[] okm = kdf.deriveData(hkdfParams);
    }

    public void demonstrateHkdfModes(byte[] ikm, byte[] salt, byte[] info, SecretKey prk) {
        // 1. Extract-then-Expand (Most common)
        HKDFParameterSpec params = HKDFParameterSpec.ofExtract()
          .addIKM(ikm)
          .addSalt(salt)
          .thenExpand(info, 32);

        // 2. Extract-only (Produces a pseudorandom key)
        HKDFParameterSpec extractOnly = HKDFParameterSpec.ofExtract()
          .addIKM(ikm)
          .addSalt(salt)
          .extractOnly();

        // 3. Expand-only (Uses a previously derived PRK)
        HKDFParameterSpec expandOnly = HKDFParameterSpec.expandOnly(prk, info, 64);
    }

    public void compareOldWay(byte[] salt, byte[] ikm, byte[] info) throws Exception {
        // Manual implementation using Mac (The "Old Way")
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(salt, "HmacSHA256"));
        byte[] prk = mac.doFinal(ikm);

        mac.init(new SecretKeySpec(prk, "HmacSHA256"));
        byte[] t = new byte[0];
        byte[] okm = new byte[32];
        byte counter = 1;
        mac.update(t);
        mac.update(info);
        mac.update(counter);
        t = mac.doFinal();
        System.arraycopy(t, 0, okm, 0, 32);
        SecretKey aesKey = new SecretKeySpec(okm, "AES");
    }

    public void compareNewWay(byte[] salt, byte[] ikm, byte[] info) throws Exception {
        // Self-documenting construct (The "New Way")
        KDF hkdf = KDF.getInstance("HKDF-SHA256");
        SecretKey aesKey = hkdf.deriveKey("AES", HKDFParameterSpec.ofExtract()
          .addIKM(ikm)
          .addSalt(salt)
          .thenExpand(info, 32));
    }
}