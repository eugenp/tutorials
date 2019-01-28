package com.baeldung.hashing;

import com.google.common.hash.Hashing;
import org.apache.commons.codec.digest.DigestUtils;
import org.bouncycastle.util.encoders.Hex;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * SHA-256 hash算法
 * @author zn.wang
 */
public class SHA256Hashing {

    /**
     * 使用{@link java.security.MessageDigest#getInstance(String)} 和
     * 生成sha256的十六进制字符串
     * @param originalString
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String HashWithJavaMessageDigest(final String originalString) throws NoSuchAlgorithmException {
        final MessageDigest digest = MessageDigest.getInstance("SHA-256");
        final byte[] encodedhash = digest.digest(originalString.getBytes(StandardCharsets.UTF_8));
        return bytesToHex(encodedhash);
    }

    /**
     * 使用guava的{@link com.google.common.hash.Hashing#sha256()}
     * 生成sha256的十六进制字符串
     * @param originalString
     * @return
     */
    public static String HashWithGuava(final String originalString) {
        final String sha256hex = Hashing.sha256().hashString(originalString, StandardCharsets.UTF_8).toString();
        return sha256hex;
    }

    /**
     * 使用guava的{@link org.apache.commons.codec.digest.DigestUtils#sha256Hex(String)}
     * 生成sha256的十六进制字符串
     * @param originalString
     * @return
     */
    public static String HashWithApacheCommons(final String originalString) {
        final String sha256hex = DigestUtils.sha256Hex(originalString);
        return sha256hex;
    }

    /**
     * 使用{@link java.security.MessageDigest#getInstance(String)} 和
     * {@link org.bouncycastle.util.encoders.Hex#encode(byte[])}
     * 生成sha256的十六进制字符串
     * @param originalString
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String HashWithBouncyCastle(final String originalString) throws NoSuchAlgorithmException {
        final MessageDigest digest = MessageDigest.getInstance("SHA-256");
        final byte[] hash = digest.digest(originalString.getBytes(StandardCharsets.UTF_8));
        final String sha256hex = new String(Hex.encode(hash));
        return sha256hex;
    }

    private static String bytesToHex(byte[] hash) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if (hex.length() == 1){
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
