package com.baeldung.tink;

import com.google.crypto.tink.*;
import com.google.crypto.tink.aead.AeadConfig;
import com.google.crypto.tink.aead.AeadFactory;
import com.google.crypto.tink.aead.AeadKeyTemplates;
import com.google.crypto.tink.config.TinkConfig;
import com.google.crypto.tink.hybrid.HybridDecryptFactory;
import com.google.crypto.tink.hybrid.HybridEncryptFactory;
import com.google.crypto.tink.hybrid.HybridKeyTemplates;
import com.google.crypto.tink.mac.MacFactory;
import com.google.crypto.tink.mac.MacKeyTemplates;
import com.google.crypto.tink.signature.PublicKeySignFactory;
import com.google.crypto.tink.signature.PublicKeyVerifyFactory;
import com.google.crypto.tink.signature.SignatureKeyTemplates;
import org.junit.Assert;
import org.junit.Test;

import java.security.GeneralSecurityException;

public class TinkLiveTest {

    //need to download policy files and put them into ${java.home}/jre/lib/security/

    private static final String PLAINTEXT = "BAELDUNG";
    private static final String DATA = "TINK";

    @Test
    public void givenPlaintext_whenEncryptWithAead_thenPlaintextIsEncrypted() throws GeneralSecurityException {

        AeadConfig.register();

        KeysetHandle keysetHandle = KeysetHandle.generateNew(
                AeadKeyTemplates.AES256_GCM);

        Aead aead = AeadFactory.getPrimitive(keysetHandle);

        byte[] ciphertext = aead.encrypt(PLAINTEXT.getBytes(),
                DATA.getBytes());

        Assert.assertNotEquals(PLAINTEXT, new String(ciphertext));
    }

    @Test
    public void givenData_whenComputeMAC_thenVerifyMAC() throws GeneralSecurityException {

        TinkConfig.register();

        KeysetHandle keysetHandle = KeysetHandle.generateNew(
                MacKeyTemplates.HMAC_SHA256_128BITTAG);

        Mac mac = MacFactory.getPrimitive(keysetHandle);

        byte[] tag = mac.computeMac(DATA.getBytes());

        mac.verifyMac(tag, DATA.getBytes());
    }

    @Test
    public void givenData_whenSignData_thenVerifySignature() throws GeneralSecurityException {

        TinkConfig.register();

        KeysetHandle privateKeysetHandle = KeysetHandle.generateNew(
                SignatureKeyTemplates.ECDSA_P256);

        PublicKeySign signer = PublicKeySignFactory.getPrimitive(privateKeysetHandle);

        byte[] signature = signer.sign(DATA.getBytes());

        KeysetHandle publicKeysetHandle =
                privateKeysetHandle.getPublicKeysetHandle();

        PublicKeyVerify verifier = PublicKeyVerifyFactory.getPrimitive(publicKeysetHandle);

        verifier.verify(signature, DATA.getBytes());
    }

    @Test
    public void givenPlaintext_whenEncryptWithHybridEncryption_thenVerifyDecryptedIsEqual() throws GeneralSecurityException {

        TinkConfig.register();

        KeysetHandle privateKeysetHandle = KeysetHandle.generateNew(
                HybridKeyTemplates.ECIES_P256_HKDF_HMAC_SHA256_AES128_CTR_HMAC_SHA256);

        KeysetHandle publicKeysetHandle = privateKeysetHandle.getPublicKeysetHandle();

        HybridEncrypt hybridEncrypt = HybridEncryptFactory.getPrimitive(publicKeysetHandle);

        HybridDecrypt hybridDecrypt = HybridDecryptFactory.getPrimitive(privateKeysetHandle);

        String contextInfo = "Tink";

        byte[] ciphertext = hybridEncrypt.encrypt(PLAINTEXT.getBytes(), contextInfo.getBytes());

        byte[] plaintextDecrypted = hybridDecrypt.decrypt(ciphertext, contextInfo.getBytes());

        Assert.assertEquals(PLAINTEXT,new String(plaintextDecrypted));
    }
}


