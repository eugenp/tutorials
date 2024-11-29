package com.baeldung.bouncycastle.pgp;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Iterator;

import org.bouncycastle.bcpg.ArmoredOutputStream;
import org.bouncycastle.bcpg.CompressionAlgorithmTags;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openpgp.PGPCompressedData;
import org.bouncycastle.openpgp.PGPEncryptedData;
import org.bouncycastle.openpgp.PGPEncryptedDataGenerator;
import org.bouncycastle.openpgp.PGPEncryptedDataList;
import org.bouncycastle.openpgp.PGPException;
import org.bouncycastle.openpgp.PGPLiteralData;
import org.bouncycastle.openpgp.PGPOnePassSignatureList;
import org.bouncycastle.openpgp.PGPPrivateKey;
import org.bouncycastle.openpgp.PGPPublicKey;
import org.bouncycastle.openpgp.PGPPublicKeyEncryptedData;
import org.bouncycastle.openpgp.PGPSecretKeyRingCollection;
import org.bouncycastle.openpgp.PGPUtil;
import org.bouncycastle.openpgp.jcajce.JcaPGPObjectFactory;
import org.bouncycastle.openpgp.operator.PGPDataEncryptorBuilder;
import org.bouncycastle.openpgp.operator.jcajce.JcaKeyFingerprintCalculator;
import org.bouncycastle.openpgp.operator.jcajce.JcePGPDataEncryptorBuilder;
import org.bouncycastle.openpgp.operator.jcajce.JcePublicKeyDataDecryptorFactoryBuilder;
import org.bouncycastle.openpgp.operator.jcajce.JcePublicKeyKeyEncryptionMethodGenerator;
import org.bouncycastle.util.io.Streams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BouncyCastlePGPDemoApplication {

    private static final Logger logger = LoggerFactory.getLogger(BouncyCastlePGPDemoApplication.class);

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    public static void main(String[] args) {

        Path resourcesPath = Paths.get("src", "main", "resources");
        String pgpresource = resourcesPath.resolve("pgp")
            .toAbsolutePath()
            .toString();
        String pubKeyFileName = pgpresource + "/public_key.asc";
        String encryptedFileName = pgpresource + "/EncryptedOutputFile.pgp";
        String plainTextInputFileName = pgpresource + "/PlainTextInputFile.txt";
        String privKeyFileName = pgpresource + "/private_key.asc";

        try {
            encryptFile(encryptedFileName, plainTextInputFileName, pubKeyFileName, true);
            decryptFile(encryptedFileName, privKeyFileName, "baeldung".toCharArray(), "decryptedFile", true);
        } catch (NoSuchProviderException e) {
            logger.error(e.getMessage());
        } catch (IOException e) {
            logger.error(e.getMessage());
        } catch (PGPException e) {
            logger.error(e.getMessage());
        }

    }

    public static void encryptFile(String outputFileName, String inputFileName, String pubKeyFileName, boolean armor) throws IOException, NoSuchProviderException, PGPException {

        OutputStream out = new BufferedOutputStream(new FileOutputStream(outputFileName));
        PGPPublicKey encKey = PGPExampleUtil.readPublicKey(pubKeyFileName);
        if (armor) {
            out = new ArmoredOutputStream(out);
        }
        try {
            byte[] bytes = PGPExampleUtil.compressFile(inputFileName, CompressionAlgorithmTags.ZIP);
            PGPDataEncryptorBuilder encryptorBuilder = new JcePGPDataEncryptorBuilder(PGPEncryptedData.CAST5).setProvider("BC")
                .setSecureRandom(new SecureRandom())
                .setWithIntegrityPacket(true);
            PGPEncryptedDataGenerator encGen = new PGPEncryptedDataGenerator(encryptorBuilder);
            encGen.addMethod(new JcePublicKeyKeyEncryptionMethodGenerator(encKey).setProvider("BC"));
            OutputStream cOut = encGen.open(out, bytes.length);
            cOut.write(bytes);
            cOut.close();
            out.close();
        } catch (PGPException e) {
            logger.error(e.getMessage());
        }
    }

    public static void decryptFile(String encryptedInputFileName, String privatekeyFileName, char[] passPhrase, String defaultFileName, boolean withIntegrityCheck) throws IOException, NoSuchProviderException {
        InputStream instream = new BufferedInputStream(new FileInputStream(encryptedInputFileName));
        InputStream privateKeyInStream = new BufferedInputStream(new FileInputStream(privatekeyFileName));
        instream = PGPUtil.getDecoderStream(instream);

        try {
            JcaPGPObjectFactory pgpF = new JcaPGPObjectFactory(instream);
            PGPEncryptedDataList enc;
            Object o = pgpF.nextObject();
            // the first object might be a PGP marker packet.
            if (o instanceof PGPEncryptedDataList) {
                enc = (PGPEncryptedDataList) o;
            } else {
                enc = (PGPEncryptedDataList) pgpF.nextObject();
            }
            Iterator it = enc.getEncryptedDataObjects();
            PGPPrivateKey sKey = null;
            PGPPublicKeyEncryptedData pbe = null;
            PGPSecretKeyRingCollection pgpSec = new PGPSecretKeyRingCollection(PGPUtil.getDecoderStream(privateKeyInStream), new JcaKeyFingerprintCalculator());
            while (sKey == null && it.hasNext()) {
                pbe = (PGPPublicKeyEncryptedData) it.next();
                sKey = PGPExampleUtil.findSecretKey(pgpSec, pbe.getKeyID(), passPhrase);
            }
            if (sKey == null) {
                throw new IllegalArgumentException("secret key for message not found.");
            }
            InputStream clear = pbe.getDataStream(new JcePublicKeyDataDecryptorFactoryBuilder().setProvider("BC")
                .build(sKey));
            JcaPGPObjectFactory plainFact = new JcaPGPObjectFactory(clear);
            Object message = plainFact.nextObject();
            if (message instanceof PGPCompressedData) {
                PGPCompressedData cData = (PGPCompressedData) message;
                JcaPGPObjectFactory pgpFact = new JcaPGPObjectFactory(cData.getDataStream());
                message = pgpFact.nextObject();
            }
            if (message instanceof PGPLiteralData) {
                PGPLiteralData ld = (PGPLiteralData) message;
                String outFileName = ld.getFileName();
                outFileName = defaultFileName;
                InputStream unc = ld.getInputStream();
                OutputStream fOut = new FileOutputStream(outFileName);
                Streams.pipeAll(unc, fOut);
                fOut.close();
            } else if (message instanceof PGPOnePassSignatureList) {
                throw new PGPException("encrypted message contains a signed message - not literal data.");
            } else {
                throw new PGPException("message is not a simple encrypted file - type unknown.");
            }
            if (pbe.isIntegrityProtected() && pbe.verify()) {
                logger.error("message integrity check passed");
            } else {
                logger.error("message integrity check failed");
            }
        } catch (PGPException e) {
            logger.error(e.getMessage());
        }
        privateKeyInStream.close();
        instream.close();
    }
}
