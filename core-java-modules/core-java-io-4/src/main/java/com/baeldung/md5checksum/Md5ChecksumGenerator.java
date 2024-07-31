package com.baeldung.md5checksum;

import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import com.google.common.io.ByteSource;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5ChecksumGenerator {

    public static String genWithApacheCommons(String filePath) throws IOException {
        try (InputStream is = Files.newInputStream(Paths.get(filePath))) {
            return DigestUtils.md5Hex(is);
        }
    }

    public static String genWithGuava(String filePath) throws IOException {
        File file = new File(filePath);
        ByteSource byteSource = com.google.common.io.Files.asByteSource(file);
        HashCode hc = byteSource.hash(Hashing.md5());
        return hc.toString();
    }

    public static String genWithMessageDigest(String filePath) throws IOException, NoSuchAlgorithmException {
        byte[] data = Files.readAllBytes(Paths.get(filePath));
        byte[] hash = MessageDigest.getInstance("MD5").digest(data);
        return new BigInteger(1, hash).toString(16);
    }

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        String filePath = "D:\\temp.txt";
        System.out.println(genWithApacheCommons(filePath));
        System.out.println(genWithMessageDigest(filePath));
        System.out.println(genWithGuava(filePath));
    }
}
