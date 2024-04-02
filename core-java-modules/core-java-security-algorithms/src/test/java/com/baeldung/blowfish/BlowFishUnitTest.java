package com.baeldung.blowfish;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class BlowFishUnitTest {

    @Test
    public void givenBlowfishAlogrithm_whenEncryptAndDecryptString_thenCompareResults() throws Exception {
        String secretMessage = "Secret message to encrypt";
        String secretKey = "MyKey123";
        byte[] keyData = secretKey.getBytes();

        // Encryption
        SecretKeySpec secretKeySpec = new SecretKeySpec(keyData, "Blowfish");
        Cipher encryptCipher = Cipher.getInstance("Blowfish");
        encryptCipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        byte[] encryptedBytes = encryptCipher.doFinal(secretMessage.getBytes(StandardCharsets.UTF_8));
        String encryptedtext = Base64.getEncoder().encodeToString(encryptedBytes);

        // Decryption
        byte[] ecryptedtexttobytes = Base64.getDecoder().decode(encryptedtext);
        Cipher decryptCipher = Cipher.getInstance("Blowfish");
        decryptCipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        byte[] decrypted = decryptCipher.doFinal(ecryptedtexttobytes);
        String decrypedText = new String(decrypted, StandardCharsets.UTF_8);

        Assertions.assertEquals(secretMessage, decrypedText);
    }

    @Test
    public void givenBlowfishAlogrithm_whenEncryptAndDecryptFile_thenCompareResults() throws Exception {
        String secretKey = "MyKey123";
        byte[] keyData = secretKey.getBytes();
        SecretKeySpec secretKeySpec = new SecretKeySpec(keyData, "Blowfish");

        String originalContent = "some secret text file";
        Path tempFile = Files.createTempFile("temp", "txt");
        writeFile(tempFile, originalContent);

        // Encryption
        byte[] fileBytes = Files.readAllBytes(tempFile);
        Cipher encryptCipher = Cipher.getInstance("Blowfish");
        encryptCipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        byte[] encryptedFileBytes = encryptCipher.doFinal(fileBytes);
        try (FileOutputStream stream = new FileOutputStream(tempFile.toFile())) {
            stream.write(encryptedFileBytes);
        }

        // Decryption
        encryptedFileBytes = Files.readAllBytes(tempFile);
        Cipher decryptCipher = Cipher.getInstance("Blowfish");
        decryptCipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        byte[] decryptedFileBytes = decryptCipher.doFinal(encryptedFileBytes);
        try (FileOutputStream stream = new FileOutputStream(tempFile.toFile())) {
            stream.write(decryptedFileBytes);
        }

        String fileContent = readFile(tempFile);

        Assertions.assertEquals(originalContent, fileContent);
    }

    private void writeFile(Path path, String content) throws Exception {
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            writer.write(content);
        }
    }

    private String readFile(Path path) throws Exception {
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
