package com.baeldung.certificate;

import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x500.style.IETFUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.cryptacular.util.CertUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.naming.ldap.LdapName;
import javax.naming.ldap.Rdn;
import javax.security.auth.x500.X500Principal;
import java.io.FileInputStream;
import java.security.Security;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExtractCommonNameUnitTest {

    private static final String EXPECTED_CN = "Baeldung";

    private String certificatePath = "src/main/resources/Baeldung.cer";

    private X509Certificate certificate;

    @BeforeEach
    public void setUp() throws Exception {
        Security.addProvider(new BouncyCastleProvider());
        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509", "BC");
        certificate = (X509Certificate) certificateFactory.generateCertificate(new FileInputStream(certificatePath));
    }

    @Test
    void whenUsingBouncyCastle_thenExtractCommonName() {
        X500Principal principal = certificate.getSubjectX500Principal();
        X500Name x500Name = new X500Name(principal.getName());
        RDN[] rdns = x500Name.getRDNs(BCStyle.CN);
        List<String> names = new ArrayList<>();
        for (RDN rdn : rdns) {
            String name = IETFUtils.valueToString(rdn.getFirst().getValue());
            names.add(name);
        }

        for (String commonName : names) {
            assertEquals(EXPECTED_CN, commonName);
        }
    }

    @Test
    void whenUsingLDAPAPI_thenExtractCommonName() throws Exception {
        X500Principal principal = certificate.getSubjectX500Principal();
        LdapName ldapDN = new LdapName(principal.getName());
        List<String> names = new ArrayList<>();
        for (Rdn rdn : ldapDN.getRdns()) {
            if (rdn.getType().equalsIgnoreCase("cn")) {
                String name = rdn.getValue().toString();
                names.add(name);
            }
        }

        for (String commonName : names) {
            assertEquals(EXPECTED_CN, commonName);
        }
    }

    @Test
    void whenUsingCryptacular_thenExtractCommonName() {
        String commonName = CertUtil.subjectCN(certificate);
        assertEquals(EXPECTED_CN, commonName);
    }

    @Test
    void whenUsingRegex_thenExtractCommonName() {
        X500Principal principal = certificate.getSubjectX500Principal();
        List<String> names = new ArrayList<>();
        Pattern pattern = Pattern.compile("CN=([^,]+)");
        Matcher matcher = pattern.matcher(principal.getName());
        while (matcher.find()) {
            names.add(matcher.group(1));
        }

        for (String commonName : names) {
            assertEquals(EXPECTED_CN, commonName);
        }
    }
}
