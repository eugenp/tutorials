package com.baeldung.certificate;

import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Enumeration;

public class RootCertificateUtil {

    private RootCertificateUtil() {
    }

    public static X509Certificate getRootCertificate(X509Certificate endEntityCertificate, KeyStore trustStore)
            throws Exception {
        X509Certificate issuerCertificate = findIssuerCertificate(endEntityCertificate, trustStore);
        if (issuerCertificate != null) {
            if (isRoot(issuerCertificate)) {
                return issuerCertificate;
            } else {
                return getRootCertificate(issuerCertificate, trustStore);
            }
        }
        return null;
    }

    private static X509Certificate findIssuerCertificate(X509Certificate certificate, KeyStore trustStore)
            throws KeyStoreException {
        Enumeration<String> aliases = trustStore.aliases();
        while (aliases.hasMoreElements()) {
            String alias = aliases.nextElement();
            Certificate cert = trustStore.getCertificate(alias);
            if (cert instanceof X509Certificate) {
                X509Certificate x509Cert = (X509Certificate) cert;
                if (x509Cert.getSubjectX500Principal().equals(certificate.getIssuerX500Principal())) {
                    return x509Cert;
                }
            }
        }
        return null;
    }

    private static boolean isRoot(X509Certificate certificate) {
        try {
            certificate.verify(certificate.getPublicKey());
            return certificate.getKeyUsage() != null && certificate.getKeyUsage()[5];
        } catch (Exception e) {
            return false;
        }
    }
}
