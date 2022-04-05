package com.baeldung.keystore;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import sun.security.x509.AlgorithmId;
import sun.security.x509.CertificateAlgorithmId;
import sun.security.x509.CertificateSerialNumber;
import sun.security.x509.CertificateValidity;
import sun.security.x509.CertificateVersion;
import sun.security.x509.CertificateX509Key;
import sun.security.x509.SubjectAlternativeNameExtension;
import sun.security.x509.X500Name;
import sun.security.x509.X509CertImpl;
import sun.security.x509.X509CertInfo;
import sun.security.x509.CertificateExtensions;
import sun.security.x509.GeneralNames;
import sun.security.x509.GeneralName;
import sun.security.x509.GeneralNameInterface;
import sun.security.x509.DNSName;
import sun.security.x509.IPAddressName;
import sun.security.util.DerOutputStream;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.SignatureException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Date;

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

    @Before
    public void setUp() throws Exception {
        //using java cryptography extension keyStore instead of Keystore.getDefaultType
        keyStore = new JavaKeyStore(KEY_STORE_TYPE, KEYSTORE_PWD, KEYSTORE_NAME);
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

    private X509Certificate generateSelfSignedCertificate(KeyPair keyPair) throws CertificateException, IOException, NoSuchProviderException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        X509CertInfo certInfo = new X509CertInfo();
        // Serial number and version
        certInfo.set(X509CertInfo.SERIAL_NUMBER, new CertificateSerialNumber(new BigInteger(64, new SecureRandom())));
        certInfo.set(X509CertInfo.VERSION, new CertificateVersion(CertificateVersion.V3));

        // Subject & Issuer
        X500Name owner = new X500Name(DN_NAME);
        certInfo.set(X509CertInfo.SUBJECT, owner);
        certInfo.set(X509CertInfo.ISSUER, owner);

        // Key and algorithm
        certInfo.set(X509CertInfo.KEY, new CertificateX509Key(keyPair.getPublic()));
        AlgorithmId algorithm = new AlgorithmId(AlgorithmId.sha1WithRSAEncryption_oid);
        certInfo.set(X509CertInfo.ALGORITHM_ID, new CertificateAlgorithmId(algorithm));

        // Validity
        Date validFrom = new Date();
        Date validTo = new Date(validFrom.getTime() + 50L * 365L * 24L * 60L * 60L * 1000L); //50 years
        CertificateValidity validity = new CertificateValidity(validFrom, validTo);
        certInfo.set(X509CertInfo.VALIDITY, validity);
        
        GeneralNameInterface dnsName = new DNSName("baeldung.com");
        DerOutputStream dnsNameOutputStream = new DerOutputStream();
        dnsName.encode(dnsNameOutputStream);
        
        GeneralNameInterface ipAddress = new IPAddressName("127.0.0.1");
        DerOutputStream ipAddressOutputStream = new DerOutputStream();
        ipAddress.encode(ipAddressOutputStream);
        
        GeneralNames generalNames = new GeneralNames();
        generalNames.add(new GeneralName(dnsName));
        generalNames.add(new GeneralName(ipAddress));
        
        CertificateExtensions ext = new CertificateExtensions();
        ext.set(SubjectAlternativeNameExtension.NAME, new SubjectAlternativeNameExtension(generalNames));

        certInfo.set(X509CertInfo.EXTENSIONS, ext);        

        // Create certificate and sign it
        X509CertImpl cert = new X509CertImpl(certInfo);
        cert.sign(keyPair.getPrivate(), SHA1WITHRSA);

        // Since the SHA1withRSA provider may have a different algorithm ID to what we think it should be,
        // we need to reset the algorithm ID, and resign the certificate
        AlgorithmId actualAlgorithm = (AlgorithmId) cert.get(X509CertImpl.SIG_ALG);
        certInfo.set(CertificateAlgorithmId.NAME + "." + CertificateAlgorithmId.ALGORITHM, actualAlgorithm);
        X509CertImpl newCert = new X509CertImpl(certInfo);
        newCert.sign(keyPair.getPrivate(), SHA1WITHRSA);

        return newCert;
    }

}