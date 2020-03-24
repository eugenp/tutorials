package com.baeldung.hashing;

import com.google.common.hash.Hashing;
import org.apache.commons.codec.digest.DigestUtils;
import org.bouncycastle.crypto.digests.SHA3Digest;
import org.bouncycastle.jcajce.provider.digest.SHA3;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

import static com.baeldung.hashing.DigestAlgorithms.SHA3_256;
import static com.baeldung.hashing.SHACommonUtils.bytesToHex;

public class SHA3Hashing {

    /* works with JDK9+ only */
    public static String hashWithJavaMessageDigestJDK9(final String originalString) throws NoSuchAlgorithmException {
        final MessageDigest digest = MessageDigest.getInstance(SHA3_256);
        final byte[] hashbytes = digest.digest(originalString.getBytes(StandardCharsets.UTF_8));
        return bytesToHex(hashbytes);
    }

    public static String hashWithJavaMessageDigest(final String originalString) throws NoSuchAlgorithmException {
        Security.addProvider(new BouncyCastleProvider());
        final MessageDigest digest = MessageDigest.getInstance(SHA3_256);
        final byte[] hashbytes = digest.digest(originalString.getBytes(StandardCharsets.UTF_8));
        return bytesToHex(hashbytes);
    }

    /* works with JDK9+ only */
    public static String hashWithApacheCommonsJDK9(final String originalString) {
        return new DigestUtils(SHA3_256).digestAsHex(originalString);
    }

    public static String hashWithBouncyCastle(final String originalString) {
        SHA3.Digest256 digest256 = new SHA3.Digest256();
        byte[] hashbytes = digest256.digest(originalString.getBytes(StandardCharsets.UTF_8));
        return new String(Hex.encode(hashbytes));
    }

}
