package com.baeldung.des;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class TripleDESUnitTest {

    @Test
    public void given3DesKey_whenEncryptAndDecryptString_thenCompareResults() throws Exception {
        byte[] secretKey = "9mng65v8jf4lxn93nabf981m".getBytes();
        byte[] iv = "a76nb5h9".getBytes();

        String secretMessage = "Baeldung secret message";

        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey, "DESede");
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        Cipher encryptCipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
        encryptCipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivSpec);
        byte[] secretMessagesBytes = secretMessage.getBytes(StandardCharsets.UTF_8);
        byte[] encryptedMessageBytes = encryptCipher.doFinal(secretMessagesBytes);

        Cipher decryptCipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
        decryptCipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivSpec);
        byte[] decryptedMessageBytes = decryptCipher.doFinal(encryptedMessageBytes);
        String decryptedMessage = new String(decryptedMessageBytes, StandardCharsets.UTF_8);

        Assertions.assertEquals(secretMessage, decryptedMessage);
    }

    @Test
    public void given3DesKey_whenEncryptAndDecryptFile_thenCompareResults() throws Exception {
        byte[] secretKey = "9mng65v8jf4lxn93nabf981m".getBytes();
        byte[] iv = "a76nb5h9".getBytes();

        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey, "DESede");
        IvParameterSpec ivSpec = new IvParameterSpec(iv);

        String originalContent = "some secret message";
        Path tempFile = Files.createTempFile("temp", "txt");
        writeString(tempFile, originalContent);

        byte[] fileBytes = Files.readAllBytes(tempFile);
        Cipher encryptCipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
        encryptCipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivSpec);
        byte[] encryptedFileBytes = encryptCipher.doFinal(fileBytes);
        try (FileOutputStream stream = new FileOutputStream(tempFile.toFile())) {
            stream.write(encryptedFileBytes);
        }

        encryptedFileBytes = Files.readAllBytes(tempFile);
        Cipher decryptCipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
        decryptCipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivSpec);
        byte[] decryptedFileBytes = decryptCipher.doFinal(encryptedFileBytes);
        try (FileOutputStream stream = new FileOutputStream(tempFile.toFile())) {
            stream.write(decryptedFileBytes);
        }

        String fileContent = readString(tempFile);

        Assertions.assertEquals(originalContent, fileContent);
    }

    private void writeString(Path path, String content) throws Exception {
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            writer.write(content);
        }
    }

    private String readString(Path path) throws Exception {
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(path.toFile()))) {
            String line;
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line);
            }
        }
        return resultStringBuilder.toString();
    }
}
