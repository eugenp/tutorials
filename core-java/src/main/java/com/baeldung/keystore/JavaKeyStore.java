package com.baeldung.keystore;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;

/**
 * Created by adi on 3/7/18.
 */
public class JavaKeyStore {

    private KeyStore keyStore;

    public JavaKeyStore(String keystoreType, String keystorePassword, String keystoreName) throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException {
        this.keyStore = createKeyStore(keystoreType,keystorePassword,keystoreName);
    }

    public KeyStore createKeyStore(String keystoreType, String keystorePassword, String keystoreName) throws KeyStoreException, CertificateException, NoSuchAlgorithmException, IOException {
        if(keystoreType==null || keystoreType.isEmpty()){
            keystoreType = KeyStore.getDefaultType();
        }
        KeyStore ks = KeyStore.getInstance(keystoreType);
        //load
        char[] pwdArray = keystorePassword.toCharArray();
        ks.load(null, pwdArray);

        // Save the keystore
        FileOutputStream fos = new FileOutputStream(keystoreName);
        ks.store(fos, pwdArray);
        fos.close();

        return ks;
    }

    public KeyStore loadKeyStore(String keystoreType, String keystorePassword, String keystoreName) throws IOException, KeyStoreException, CertificateException, NoSuchAlgorithmException {
        if(keystoreType==null || keystoreType.isEmpty()){
            keystoreType = KeyStore.getDefaultType();
        }
        KeyStore ks = KeyStore.getInstance(keystoreType);
        char[] pwdArray = keystorePassword.toCharArray();
        ks.load(new FileInputStream(keystoreName), pwdArray);
        this.keyStore = ks;
        return ks;
    }

    public void setEntry(String alias, KeyStore.SecretKeyEntry secretKeyEntry, KeyStore.ProtectionParameter protectionParameter) throws KeyStoreException {
        this.keyStore.setEntry(alias, secretKeyEntry, protectionParameter);
    }

    public void setKeyEntry(String alias, PrivateKey privateKey, String keyPassword, Certificate[] certificateChain) throws KeyStoreException {
        this.keyStore.setKeyEntry(alias, privateKey, keyPassword.toCharArray(), certificateChain);
    }

    public void setCertificateEntry(String alias, Certificate certificate) throws KeyStoreException {
        this.keyStore.setCertificateEntry(alias, certificate);
    }

    public void deleteEntry(String alias) throws KeyStoreException {
        this.keyStore.deleteEntry(alias);
    }


    public KeyStore getKeyStore() {
        return keyStore;
    }

    public void setKeyStore(KeyStore keyStore) {
        this.keyStore = keyStore;
    }
}
