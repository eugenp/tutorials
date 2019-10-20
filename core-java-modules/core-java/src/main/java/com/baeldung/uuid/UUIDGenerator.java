package com.baeldung.uuid;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.UUID;

public class UUIDGenerator {

    /**
     * These are predefined UUID for name spaces
     */
    private static final String NAMESPACE_DNS = "6ba7b810-9dad-11d1-80b4-00c04fd430c8";
    private static final String NAMESPACE_URL = "6ba7b811-9dad-11d1-80b4-00c04fd430c8";
    private static final String NAMESPACE_OID = "6ba7b812-9dad-11d1-80b4-00c04fd430c8";
    private static final String NAMESPACE_X500 = "6ba7b814-9dad-11d1-80b4-00c04fd430c8";

    private static final char[] hexArray = "0123456789ABCDEF".toCharArray();

    public static void main(String[] args) {
        try {
            System.out.println("Type 3 : " + generateType3UUID(NAMESPACE_DNS, "google.com"));
            System.out.println("Type 4 : " + generateType4UUID());
            System.out.println("Type 5 : " + generateType5UUID(NAMESPACE_URL, "google.com"));
            System.out.println("Unique key  : " + generateUniqueKeysWithUUIDAndMessageDigest());
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * Type 4 UUID Generation
     */
    public static UUID generateType4UUID() {
        UUID uuid = UUID.randomUUID();
        return uuid;
    }

    /**
     * Type 3 UUID Generation
     * 
     * @throws UnsupportedEncodingException 
     */
    public static UUID generateType3UUID(String namespace, String name) throws UnsupportedEncodingException {
        String source = namespace + name;
        byte[] bytes = source.getBytes("UTF-8");
        UUID uuid = UUID.nameUUIDFromBytes(bytes);
        return uuid;
    }

    /**
     * Type 5 UUID Generation
     * 
     * @throws UnsupportedEncodingException 
     */
    public static UUID generateType5UUID(String namespace, String name) throws UnsupportedEncodingException {
        String source = namespace + name;
        byte[] bytes = source.getBytes("UTF-8");
        UUID uuid = type5UUIDFromBytes(bytes);
        return uuid;
    }

    public static UUID type5UUIDFromBytes(byte[] name) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException nsae) {
            throw new InternalError("SHA-1 not supported", nsae);
        }
        byte[] bytes = Arrays.copyOfRange(md.digest(name), 0, 16);
        bytes[6] &= 0x0f; /* clear version        */
        bytes[6] |= 0x50; /* set to version 5     */
        bytes[8] &= 0x3f; /* clear variant        */
        bytes[8] |= 0x80; /* set to IETF variant  */
        return constructType5UUID(bytes);
    }

    private static UUID constructType5UUID(byte[] data) {
        long msb = 0;
        long lsb = 0;
        assert data.length == 16 : "data must be 16 bytes in length";

        for (int i = 0; i < 8; i++)
            msb = (msb << 8) | (data[i] & 0xff);

        for (int i = 8; i < 16; i++)
            lsb = (lsb << 8) | (data[i] & 0xff);
        return new UUID(msb, lsb);
    }

    /**
     * Unique Keys Generation Using Message Digest and Type 4 UUID
     * 
     * @throws NoSuchAlgorithmException 
     * @throws UnsupportedEncodingException 
     */
    public static String generateUniqueKeysWithUUIDAndMessageDigest() throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest salt = MessageDigest.getInstance("SHA-256");
        salt.update(UUID.randomUUID()
            .toString()
            .getBytes("UTF-8"));
        String digest = bytesToHex(salt.digest());
        return digest;
    }

    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

}
