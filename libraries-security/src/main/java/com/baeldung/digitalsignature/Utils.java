package com.baeldung.digitalsignature;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.Signature;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.TreeSet;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Utils {

    static final Logger log = LoggerFactory.getLogger(Utils.class);

    // Add BC Provider
    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    private Utils() {
        throw new IllegalStateException("Utility class");
    }

    private static final String STORE_TYPE = "PKCS12";
    private static final char[] PASSWORD = "changeit".toCharArray();
    private static final String SENDER_KEYSTORE = "/Users/wangkan/cert/sender_keystore.p12";
    private static final String SENDER_ALIAS = "senderKeyPair";
    public static final String SIGNING_ALGORITHM = "SHA256withRSA";
    public static final String MD_ALGORITHM = "SHA-256";
    private static final String KEY_ALGORITHM = "RSA";
    public static final String CIPHER_ALGORITHM = "RSA/ECB/OAEPWITHSHA-256ANDMGF1PADDING";
    private static final String RECEIVER_KEYSTORE = "/Users/wangkan/cert/receiver_keystore.p12";
    private static final String RECEIVER_ALIAS = "receiverKeyPair";

    public static PrivateKey getPrivateKey() throws NoSuchAlgorithmException, KeyStoreException, CertificateException,
            IOException, UnrecoverableKeyException {
        KeyStore keyStore = KeyStore.getInstance(STORE_TYPE);
        keyStore.load(new FileInputStream(SENDER_KEYSTORE), PASSWORD);
        return (PrivateKey) keyStore.getKey(SENDER_ALIAS, PASSWORD);
    }

    public static PublicKey getPublicKey()
            throws NoSuchAlgorithmException, CertificateException, IOException, KeyStoreException {
        KeyStore keyStore = KeyStore.getInstance(STORE_TYPE);
        keyStore.load(new FileInputStream(RECEIVER_KEYSTORE), PASSWORD);
        Certificate certificate = keyStore.getCertificate(RECEIVER_ALIAS);
        return certificate.getPublicKey();
    }

    public static PublicKey generatePublic(String pubKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] publicBytes = Base64.decodeBase64(pubKey);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        return keyFactory.generatePublic(keySpec);
    }

    public static boolean verifyRSA(String signature, String data, PublicKey key)
            throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        byte[] sign = Base64.decodeBase64(signature);
        byte[] dat = Base64.decodeBase64(data);
        Signature rsaVerify = Signature.getInstance(SIGNING_ALGORITHM);
        rsaVerify.initVerify(key);
        rsaVerify.update(dat);
        return rsaVerify.verify(sign);
    }

    public static void genKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        keyPairGen.initialize(3072, new SecureRandom());
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        String publicKeyString = publicKey.toString();
        String privateKeyString = privateKey.toString();
        try (FileOutputStream fos = new FileOutputStream("jasypt-public.pem")) {
            fos.write(publicKeyString.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
        }
        try (FileOutputStream fos = new FileOutputStream("jasypt-private.pem")) {
            fos.write(privateKeyString.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
        }
    }

    public static String encryptMasterPassword(String privateKeyLocation, String plainText) {
        try (InputStream privateKeyInputStream = new FileInputStream(privateKeyLocation)) {
            byte[] content = new byte[privateKeyInputStream.available()];
            int count = privateKeyInputStream.read(content);
            if (count != -1) {
                byte[] decoded = Base64.decodeBase64(content);
                RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance(KEY_ALGORITHM)
                        .generatePrivate(new PKCS8EncodedKeySpec(decoded));
                Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
                cipher.init(Cipher.ENCRYPT_MODE, priKey);
                byte[] decryptedMessageHash = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
                return Base64.encodeBase64String(decryptedMessageHash);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String decryptMessage(String message, String publicKeyLocation) {
        try (InputStream publicKeyInputStream = new FileInputStream(publicKeyLocation)) {
            byte[] content = new byte[publicKeyInputStream.available()];
            int count = publicKeyInputStream.read(content);
            if (count != -1) {
                byte[] inputByte = Base64.decodeBase64(message.getBytes(StandardCharsets.UTF_8));
                byte[] decoded = Base64.decodeBase64(content);
                RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance(KEY_ALGORITHM)
                        .generatePublic(new X509EncodedKeySpec(decoded));
                Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
                cipher.init(Cipher.DECRYPT_MODE, pubKey);
                return new String(cipher.doFinal(inputByte));
            }
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
        }
        return null;
    }

    public static void getProvider() {
        TreeSet<String> algorithms = new TreeSet<>();
        Provider[] providers = Security.getProviders();
        log.info("Provider List: " + Arrays.toString(providers));
        for (Provider provider : providers) {
            for (Provider.Service service : provider.getServices())
                if (service.getType().equals("Signature")) {
                    algorithms.add(service.getAlgorithm());
                }
        }
        log.info("Signature Algorithms List: " + algorithms.toString());
    }

}
