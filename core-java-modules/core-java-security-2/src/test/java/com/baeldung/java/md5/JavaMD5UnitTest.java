package com.baeldung.java.md5;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;

import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;

public class JavaMD5UnitTest {

    String filename = "src/test/resources/test_md5.txt";
    String checksum = "5EB63BBBE01EEED093CB22BB8F5ACDC3";

    String hash = "35454B055CC325EA1AF2126E27707052";
    String password = "ILoveJava";

    @Test
    public void givenPassword_whenHashing_thenVerifying() throws NoSuchAlgorithmException {
        String hash = "35454B055CC325EA1AF2126E27707052";
        String password = "ILoveJava";

        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte[] digest = md.digest();
        String myHash = DatatypeConverter.printHexBinary(digest).toUpperCase();

        assertThat(myHash.equals(hash)).isTrue();
    }

    @Test
    public void givenFile_generatingChecksum_thenVerifying() throws NoSuchAlgorithmException, IOException {
        String filename = "src/test/resources/test_md5.txt";
        String checksum = "5EB63BBBE01EEED093CB22BB8F5ACDC3";

        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(Files.readAllBytes(Paths.get(filename)));
        byte[] digest = md.digest();
        String myChecksum = DatatypeConverter.printHexBinary(digest).toUpperCase();

        assertThat(myChecksum.equals(checksum)).isTrue();
    }

    @Test
    public void givenPassword_whenHashingUsingCommons_thenVerifying() {
        String hash = "35454B055CC325EA1AF2126E27707052";
        String password = "ILoveJava";

        String md5Hex = DigestUtils.md5Hex(password).toUpperCase();

        assertThat(md5Hex.equals(hash)).isTrue();
    }

    @Test
    public void givenFile_whenChecksumUsingGuava_thenVerifying() throws IOException {
        String filename = "src/test/resources/test_md5.txt";
        String checksum = "5EB63BBBE01EEED093CB22BB8F5ACDC3";

        HashCode hash = com.google.common.io.Files.hash(new File(filename), Hashing.md5());
        String myChecksum = hash.toString().toUpperCase();

        assertThat(myChecksum.equals(checksum)).isTrue();
    }

}
