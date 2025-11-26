package com.baeldung.kem;

import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.KEM;
import javax.crypto.SecretKey;

public class KemUtils {

    public record KemResult(SecretKey sharedSecret, byte[] encapsulation) {}

    public static KemResult encapsulate(String algorithm, PublicKey publicKey) throws Exception {
        KEM kem = KEM.getInstance(algorithm);
        KEM.Encapsulator encapsulator = kem.newEncapsulator(publicKey);
        KEM.Encapsulated result = encapsulator.encapsulate();
        return new KemResult(result.key(), result.encapsulation());
    }

    public static KemResult decapsulate(String algorithm, PrivateKey privateKey, byte[] encapsulation)
      throws Exception {
        KEM kem = KEM.getInstance(algorithm);
        KEM.Decapsulator decapsulator = kem.newDecapsulator(privateKey);
        SecretKey recoveredSecret = decapsulator.decapsulate(encapsulation);
        return new KemResult(recoveredSecret, encapsulation);
    }

}
