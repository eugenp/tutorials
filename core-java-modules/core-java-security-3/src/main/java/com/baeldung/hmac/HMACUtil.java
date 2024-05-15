package com.baeldung.hmac;

import org.apache.commons.codec.digest.HmacUtils;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.digests.MD5Digest;
import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.crypto.digests.SHA384Digest;
import org.bouncycastle.crypto.digests.SHA512Digest;
import org.bouncycastle.crypto.macs.HMac;
import org.bouncycastle.crypto.params.KeyParameter;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;


public class HMACUtil {

    public static String hmacWithJava(String algorithm, String data, String key)
        throws NoSuchAlgorithmException, InvalidKeyException {
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), algorithm);
        Mac mac = Mac.getInstance(algorithm);
        mac.init(secretKeySpec);
        return bytesToHex(mac.doFinal(data.getBytes()));
    }

    public static String hmacWithApacheCommons(String algorithm, String data, String key) {
        String hmac = new HmacUtils(algorithm, key).hmacHex(data);
        return hmac;
    }

    public static String hmacWithBouncyCastle(String algorithm, String data, String key) {
        Digest digest = getHashDigest(algorithm);
        HMac hMac = new HMac(digest);
        hMac.init(new KeyParameter(key.getBytes()));
        byte[] hmacIn = data.getBytes();
        hMac.update(hmacIn, 0, hmacIn.length);
        byte[] hmacOut = new byte[hMac.getMacSize()];
        hMac.doFinal(hmacOut, 0);
        return bytesToHex(hmacOut);
    }

    private static Digest getHashDigest(String algorithm) {
        switch (algorithm) {
          case "HmacMD5":
            return new MD5Digest();
          case "HmacSHA256":
            return new SHA256Digest();
          case "HmacSHA384":
            return new SHA384Digest();
          case "HmacSHA512":
            return new SHA512Digest();
        }
        return new SHA256Digest();
    }

    public static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (byte h : hash) {
          String hex = Integer.toHexString(0xff & h);
          if (hex.length() == 1)
            hexString.append('0');
          hexString.append(hex);
        }
        return hexString.toString();
    }
}
