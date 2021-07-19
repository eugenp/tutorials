package com.baeldung.hashing;

import org.bouncycastle.jcajce.provider.digest.Keccak;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

import static com.baeldung.hashing.DigestAlgorithms.KECCAK_256;
import static com.baeldung.hashing.SHACommonUtils.bytesToHex;

public class Keccak256Hashing {

    public static String hashWithJavaMessageDigest(final String originalString) throws NoSuchAlgorithmException {
        Security.addProvider(new BouncyCastleProvider());
        final MessageDigest digest = MessageDigest.getInstance(KECCAK_256);
        final byte[] encodedhash = digest.digest(originalString.getBytes(StandardCharsets.UTF_8));
        return bytesToHex(encodedhash);
    }

    public static String hashWithBouncyCastle(final String originalString) {
        Keccak.Digest256 digest256 = new Keccak.Digest256();
        byte[] hashbytes = digest256.digest(originalString.getBytes(StandardCharsets.UTF_8));
        return new String(Hex.encode(hashbytes));
    }

}
