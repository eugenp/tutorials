package com.baeldung.keystore;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.OperatorCreationException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.*;
import org.bouncycastle.cert.*;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;

import java.math.BigInteger;

import java.security.Security;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Date;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.SecureRandom;


/**
 * Created by adi on 4/14/18.
 */
public class JavaKeyStoreUnitTest {

    private JavaKeyStore keyStore;

    private static final String KEYSTORE_PWD = "abc123";
    private static final String KEYSTORE_NAME = "myKeyStore";
    private static final String KEY_STORE_TYPE = "JCEKS";

    private static final String MY_SECRET_ENTRY = "mySecretEntry";
    private static final String DN_NAME = "CN=test, OU=test, O=test, L=test, ST=test, C=CY";
    private static final String SHA1WITHRSA = "SHA1withRSA";
    private static final String MY_PRIVATE_KEY = "myPrivateKey";
    private static final String MY_CERTIFICATE = "myCertificate";

    private static final String PROVIDER = "BC";
    private static final int VALIDITY_DAYS = 365 * 50;

    @Before
    public void setUp() throws Exception {
        //using java cryptography extension keyStore instead of Keystore.getDefaultType
        keyStore = new JavaKeyStore(KEY_STORE_TYPE, KEYSTORE_PWD, KEYSTORE_NAME);
        Security.addProvider(new BouncyCastleProvider());
    }

    @After
    public void tearDown() throws Exception {
        if (keyStore.getKeyStore() != null) {
            keyStore.deleteKeyStore();
        }
    }

    @Test
    public void givenNoKeyStore_whenCreateEmptyKeyStore_thenGetKeyStoreNotNull() throws Exception {
        keyStore.createEmptyKeyStore();
        KeyStore result = keyStore.getKeyStore();
        Assert.assertNotNull(result);
    }

    @Test
    public void givenEmptyKeystore_whenLoadKeyStore_thenKeyStoreLoadedAndSizeZero() throws Exception {
        keyStore.createEmptyKeyStore();
        keyStore.loadKeyStore();
        KeyStore result = keyStore.getKeyStore();
        Assert.assertNotNull(result);
        Assert.assertTrue(result.size() == 0);
    }

    @Test
    public void givenLoadedKeyStore_whenSetEntry_thenSizeIsOneAndGetKeyNotNull() throws Exception {
        keyStore.createEmptyKeyStore();
        keyStore.loadKeyStore();

        KeyGenerator keygen = KeyGenerator.getInstance("HmacSHA256");
        SecretKey secretKey = keygen.generateKey();
        //ideally, password should be different for every key
        KeyStore.ProtectionParameter protParam = new KeyStore.PasswordProtection(KEYSTORE_PWD.toCharArray());
        KeyStore.SecretKeyEntry secretKeyEntry = new KeyStore.SecretKeyEntry(secretKey);
        keyStore.setEntry(MY_SECRET_ENTRY, secretKeyEntry, protParam);

        KeyStore result = keyStore.getKeyStore();
        Assert.assertTrue(result.size() == 1);
        KeyStore.Entry entry = keyStore.getEntry(MY_SECRET_ENTRY);
        Assert.assertTrue(entry != null);
    }

    @Test
    public void givenLoadedKeyStore_whenSetKeyEntry_thenSizeIsOneAndGetEntryNotNull() throws Exception {
        keyStore.createEmptyKeyStore();
        keyStore.loadKeyStore();

        // Generate the key pair
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(1024);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        // Generate a self signed certificate
        X509Certificate certificate = generateSelfSignedCertificate(keyPair);

        X509Certificate[] certificateChain = new X509Certificate[1];
        certificateChain[0] = certificate;
        keyStore.setKeyEntry(MY_PRIVATE_KEY, keyPair.getPrivate(), KEYSTORE_PWD, certificateChain);

        KeyStore result = keyStore.getKeyStore();
        Assert.assertTrue(result.size() == 1);
        KeyStore.Entry entry = keyStore.getEntry(MY_PRIVATE_KEY);
        Assert.assertTrue(entry != null);
    }

    @Test
    public void givenLoadedKeyStore_whenSetCertificateEntry_thenSizeIsOneAndGetCertificateEntryNotNull() throws Exception {
        keyStore.createEmptyKeyStore();
        keyStore.loadKeyStore();

        // Generate the key pair
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(1024);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        // Generate a self signed certificate
        X509Certificate certificate = generateSelfSignedCertificate(keyPair);

        keyStore.setCertificateEntry(MY_CERTIFICATE, certificate);

        KeyStore result = this.keyStore.getKeyStore();
        Assert.assertTrue(result.size() == 1);
        java.security.cert.Certificate resultCertificate = keyStore.getCertificate(MY_CERTIFICATE);
        Assert.assertNotNull(resultCertificate);
    }

    @Test
    public void givenLoadedKeyStoreWithOneEntry_whenDeleteEntry_thenKeyStoreSizeIsZero() throws Exception {
        keyStore.createEmptyKeyStore();
        keyStore.loadKeyStore();

        KeyGenerator keygen = KeyGenerator.getInstance("HmacSHA256");
        SecretKey secretKey = keygen.generateKey();
        //ideally, password should be different for every key
        KeyStore.ProtectionParameter protParam = new KeyStore.PasswordProtection(KEYSTORE_PWD.toCharArray());
        KeyStore.SecretKeyEntry secretKeyEntry = new KeyStore.SecretKeyEntry(secretKey);
        keyStore.setEntry(MY_SECRET_ENTRY, secretKeyEntry, protParam);

        keyStore.deleteEntry(MY_SECRET_ENTRY);

        KeyStore result = this.keyStore.getKeyStore();
        Assert.assertTrue(result.size() == 0);
    }

    @Test
    public void givenLoadedKeystore_whenDeleteKeyStore_thenKeyStoreIsNull() throws Exception {
        keyStore.createEmptyKeyStore();
        keyStore.loadKeyStore();

        keyStore.deleteKeyStore();

        KeyStore result = this.keyStore.getKeyStore();
        Assert.assertTrue(result == null);
    }

    private X509Certificate generateSelfSignedCertificate(KeyPair keyPair) throws CertificateException, IOException, OperatorCreationException {

        X500Name issuer = new X500Name(DN_NAME);
        BigInteger serialNumber = new BigInteger(64, new SecureRandom());
        Date validFrom = new Date();
        Date validTo = new Date(validFrom.getTime() + VALIDITY_DAYS * 24L * 60L * 60L * 1000L);

        X509v3CertificateBuilder certBuilder = new JcaX509v3CertificateBuilder(issuer, serialNumber, validFrom, validTo, issuer, keyPair.getPublic());

        GeneralNames subjectAltNames = new GeneralNames(
            new GeneralName[] { new GeneralName(GeneralName.dNSName, "baeldung.com"), new GeneralName(GeneralName.iPAddress, "127.0.0.1") });

        certBuilder.addExtension(Extension.subjectAlternativeName, false, subjectAltNames);

        ContentSigner signer = new JcaContentSignerBuilder(SHA1WITHRSA).setProvider(PROVIDER)
            .build(keyPair.getPrivate());

        return new JcaX509CertificateConverter().setProvider(PROVIDER)
            .getCertificate(certBuilder.build(signer));
    }

}