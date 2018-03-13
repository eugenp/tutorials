/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.catalina.valves;

import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.logging.Level;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.apache.catalina.Globals;
import org.apache.catalina.Valve;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.connector.Request;
import org.apache.tomcat.unittest.TesterLogValidationFilter;
import org.easymock.EasyMock;

public class TestSSLValve {

    public static class MockRequest extends Request {

        public MockRequest() {
            setConnector(EasyMock.createMock(Connector.class));
            setCoyoteRequest(new org.apache.coyote.Request());
        }

        @Override
        public void setAttribute(String name, Object value) {
            getCoyoteRequest().getAttributes().put(name, value);
        }

        @Override
        public Object getAttribute(String name) {
            return getCoyoteRequest().getAttribute(name);
        }

        public void setHeader(String header, String value) {
            getCoyoteRequest().getMimeHeaders().setValue(header).setString(value);
        }

        public void addHeader(String header, String value) {
            getCoyoteRequest().getMimeHeaders().addValue(header).setString(value);
        }
    }

    private static final String[] CERTIFICATE_LINES = new String[] { "-----BEGIN CERTIFICATE-----",
            "MIIFXTCCA0WgAwIBAgIJANFf3YTJgYifMA0GCSqGSIb3DQEBCwUAMEUxCzAJBgNV",
            "BAYTAkFVMRMwEQYDVQQIDApTb21lLVN0YXRlMSEwHwYDVQQKDBhJbnRlcm5ldCBX",
            "aWRnaXRzIFB0eSBMdGQwHhcNMTcwNTI2MjEzNjM3WhcNMTgwNTI2MjEzNjM3WjBF",
            "MQswCQYDVQQGEwJBVTETMBEGA1UECAwKU29tZS1TdGF0ZTEhMB8GA1UECgwYSW50",
            "ZXJuZXQgV2lkZ2l0cyBQdHkgTHRkMIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIIC",
            "CgKCAgEA2ykNBanZz4cVITNpZcWNVmErUzqgSNrK361mj9vEdB1UkHatwal9jVrR",
            "QvFgfiZ8Gl+/85t0ebJhJ+rIr1ww6JE7v2s2MThENj95K5EwZOmgvw+CBlBYsFIz",
            "8BtjlVYy+v7RaGPXfjrFkexQP9UIaiIIog2ClDZirRvb+QxS930/YW5Qo+X6EX6W",
            "/m/HvlorD25U4ni2FQ0y+EMO2e1jD88cAAMoP5f+Mf6NBK8I6yUeaSuMq7WqtHGV",
            "e4F1WOg5z9J5c/M69rB0iQr5NUQwZ1mPYf5Kr0P6+TLh8DJphbVvmHJyT3bgofeV",
            "JYl/kdjiXS5P/jwY9tfmhu04tsyzopWRUFCcj5zCiqZYaMn0wtDn08KaAh9oOlg8",
            "Z6mJ9i5EybkLm63W7z7LxuM+qnYzq4wKkKdx8hbpASwPqzJkJeXFL/LzhKdZuHiR",
            "clgPVYnm98URwhObh073dKguG/gkhcnpXcVBBVdVTJZYGBvTpQh0afXd9bcBwOzY",
            "t4MDpGiQB2fLzBOEZhQ37kUcWPmZw5bNPxhx4yE96Md0rx/Gu4ipAHuqLemb1SL5",
            "uWNesVmgY3OXaIamQIm9BCwkf8mMvoYdAT+lukTUZLtJ6s2w+Oxnl10tmb+6sTXy",
            "UB3WcBTp/o3YjAyJPnM1Wq6nVNQ4W2+NbV5purGAP09sumxeJj8CAwEAAaNQME4w",
            "HQYDVR0OBBYEFCGOYMvymUG2ZZT+lK4LvwEvx731MB8GA1UdIwQYMBaAFCGOYMvy",
            "mUG2ZZT+lK4LvwEvx731MAwGA1UdEwQFMAMBAf8wDQYJKoZIhvcNAQELBQADggIB",
            "AG6m4nDYCompUtRVude1qulwwAaYEMHyIIsfymI8uAE7d2o4bGjVpAUOdH/VWSOp",
            "Rzx0oK6K9cHyiBlKHw5zSZdqcRi++tDX3P9Iy5tXO//zkhMEnSpk6RF2+9JXtyhx",
            "Gma4yAET1yES+ybiFT21uZrGCC9r69rWG8JRZshc4RVWGwZsd0zrATVqfY0mZurm",
            "xLgU4UOvkTczjlrLiklwwU68M1DLcILJ5FZGTWeTJ/q1wpIn9isK2siAW/VOcbuG",
            "xdbGladnIFv+iQfuZG0yjcuMsBFsQiXi6ONM8GM+dr+61V63/1s73jYcOToEsTMM",
            "3bHeVffoSkhZvOGTRCI6QhK9wqnIKhAYqu+NbV4OphfE3gOaK+T1cASXUtSQPXoa",
            "sEoIVmbQsWRBhWvYShVqvINsH/hAT3Cf/+SslprtQUqiyt2ljdgrRFZdoyB3S7ky",
            "KWoZRvHRj2cKU65LVYwx6U1A8SGmViz4aHMSai0wwKzOVv9MGHeRaVhlMmMsbdfu",
            "wKoKJv0xYoVwEh1rB8TH8PjbL+6eFLeZXYVZnH71d5JHCghZ8W0a11ZuYkscSQWk",
            "yoTBqEpJloWksrypqp3iL4PAL5+KkB2zp66+MVAg8LcEDFJggBBJCtv4SCWV7ZOB",
            "WLu8gep+XCwSn0Wb6D3eFs4DoIiMvQ6g2rS/pk7o5eWj", "-----END CERTIFICATE-----" };

    private SSLValve valve = new SSLValve();

    private MockRequest mockRequest = new MockRequest();
    private Valve mockNext = EasyMock.createMock(Valve.class);


    @Before
    public void setUp() throws Exception {
        valve.setNext(mockNext);
        mockNext.invoke(mockRequest, null);
        EasyMock.replay(mockNext);
    }


    @Test
    public void testSslHeader() {
        final String headerName = "myheader";
        final String headerValue = "BASE64_HEADER_VALUE";
        mockRequest.setHeader(headerName, headerValue);

        Assert.assertEquals(headerValue, valve.mygetHeader(mockRequest, headerName));
    }


    @Test
    public void testSslHeaderNull() {
        final String headerName = "myheader";
        mockRequest.setHeader(headerName, null);

        Assert.assertNull(valve.mygetHeader(mockRequest, headerName));
    }


    @Test
    public void testSslHeaderNullModHeader() {
        final String headerName = "myheader";
        final String nullModHeaderValue = "(null)";
        mockRequest.setHeader(headerName, nullModHeaderValue);

        Assert.assertNull(valve.mygetHeader(mockRequest, nullModHeaderValue));
    }


    @Test
    public void testSslHeaderNullName() throws Exception {
        Assert.assertNull(valve.mygetHeader(mockRequest, null));
    }


    @Test
    public void testSslHeaderMultiples() throws Exception {
        final String headerName = "myheader";
        final String headerValue = "BASE64_HEADER_VALUE";
        mockRequest.addHeader(headerName, headerValue);
        mockRequest.addHeader(headerName, "anyway won't be found");

        Assert.assertEquals(headerValue, valve.mygetHeader(mockRequest, headerName));
    }


    @Test
    public void testSslClientCertHeaderSingleSpace() throws Exception {
        String singleSpaced = certificateSingleLine(" ");
        mockRequest.setHeader(valve.getSslClientCertHeader(), singleSpaced);

        valve.invoke(mockRequest, null);

        assertCertificateParsed();
    }


    @Test
    public void testSslClientCertHeaderMultiSpace() throws Exception {
        String singleSpaced = certificateSingleLine("    ");
        mockRequest.setHeader(valve.getSslClientCertHeader(), singleSpaced);

        valve.invoke(mockRequest, null);

        assertCertificateParsed();
    }


    @Test
    public void testSslClientCertHeaderTab() throws Exception {
        String singleSpaced = certificateSingleLine("\t");
        mockRequest.setHeader(valve.getSslClientCertHeader(), singleSpaced);

        valve.invoke(mockRequest, null);

        assertCertificateParsed();
    }


    @Test
    public void testSslClientCertNull() throws Exception {
        TesterLogValidationFilter f = TesterLogValidationFilter.add(null, "", null,
                "org.apache.catalina.valves.SSLValve");

        valve.invoke(mockRequest, null);

        EasyMock.verify(mockNext);
        Assert.assertNull(mockRequest.getAttribute(Globals.CERTIFICATES_ATTR));
        Assert.assertEquals(0, f.getMessageCount());
    }


    @Test
    public void testSslClientCertShorter() throws Exception {
        mockRequest.setHeader(valve.getSslClientCertHeader(), "shorter than hell");

        TesterLogValidationFilter f = TesterLogValidationFilter.add(null, "", null,
                "org.apache.catalina.valves.SSLValve");

        valve.invoke(mockRequest, null);

        EasyMock.verify(mockNext);
        Assert.assertNull(mockRequest.getAttribute(Globals.CERTIFICATES_ATTR));
        Assert.assertEquals(0, f.getMessageCount());
    }


    @Test
    public void testSslClientCertIgnoredBegin() throws Exception {
        String[] linesBegin = Arrays.copyOf(CERTIFICATE_LINES, CERTIFICATE_LINES.length);
        linesBegin[0] = "3fisjcme3kdsakasdfsadkafsd3";
        String begin = certificateSingleLine(linesBegin, " ");
        mockRequest.setHeader(valve.getSslClientCertHeader(), begin);

        valve.invoke(mockRequest, null);

        assertCertificateParsed();
    }


    @Test
    public void testSslClientCertBadFormat() throws Exception {
        String[] linesDeleted = Arrays.copyOf(CERTIFICATE_LINES, CERTIFICATE_LINES.length / 2);
        String deleted = certificateSingleLine(linesDeleted, " ");
        mockRequest.setHeader(valve.getSslClientCertHeader(), deleted);

        TesterLogValidationFilter f = TesterLogValidationFilter.add(Level.WARNING, null,
                "java.security.cert.CertificateException", "org.apache.catalina.valves.SSLValve");

        valve.invoke(mockRequest, null);

        EasyMock.verify(mockNext);
        Assert.assertNull(mockRequest.getAttribute(Globals.CERTIFICATES_ATTR));
        Assert.assertEquals(1, f.getMessageCount());
    }


    @Test
    public void testClientCertProviderNotFound() throws Exception {
        EasyMock.expect(mockRequest.getConnector().getProperty("clientCertProvider")).andStubReturn("wontBeFound");
        EasyMock.replay(mockRequest.getConnector());
        mockRequest.setHeader(valve.getSslClientCertHeader(), certificateSingleLine(" "));

        TesterLogValidationFilter f = TesterLogValidationFilter.add(Level.SEVERE, null,
                "java.security.NoSuchProviderException", "org.apache.catalina.valves.SSLValve");

        valve.invoke(mockRequest, null);

        Assert.assertNull(mockRequest.getAttribute(Globals.CERTIFICATES_ATTR));
        Assert.assertEquals(1, f.getMessageCount());
    }


    @Test
    public void testSslCipherHeaderPresent() throws Exception {
        String cipher = "ciphered-with";
        mockRequest.setHeader(valve.getSslCipherHeader(), cipher);

        valve.invoke(mockRequest, null);

        Assert.assertEquals(cipher, mockRequest.getAttribute(Globals.CIPHER_SUITE_ATTR));
    }


    @Test
    public void testSslSessionIdHeaderPresent() throws Exception {
        String session = "ssl-session";
        mockRequest.setHeader(valve.getSslSessionIdHeader(), session);

        valve.invoke(mockRequest, null);

        Assert.assertEquals(session, mockRequest.getAttribute(Globals.SSL_SESSION_ID_ATTR));
    }


    @Test
    public void testSslCipherUserKeySizeHeaderPresent() throws Exception {
        Integer keySize = Integer.valueOf(452);
        mockRequest.setHeader(valve.getSslCipherUserKeySizeHeader(), String.valueOf(keySize));

        valve.invoke(mockRequest, null);

        Assert.assertEquals(keySize, mockRequest.getAttribute(Globals.KEY_SIZE_ATTR));
    }


    @Test(expected = NumberFormatException.class)
    public void testSslCipherUserKeySizeHeaderBadFormat() throws Exception {
        mockRequest.setHeader(valve.getSslCipherUserKeySizeHeader(), "not-an-integer");

        try {
            valve.invoke(mockRequest, null);
        } catch (NumberFormatException e) {
            Assert.assertNull(mockRequest.getAttribute(Globals.KEY_SIZE_ATTR));
            throw e;
        }
    }


    private static String certificateSingleLine(String[] lines, String separator) {
        StringBuilder singleSpaced = new StringBuilder();
        for (String current : lines) {
            singleSpaced.append(current).append(separator);
        }
        singleSpaced.deleteCharAt(singleSpaced.length() - 1);
        return singleSpaced.toString();
    }


    private static String certificateSingleLine(String separator) {
        return certificateSingleLine(CERTIFICATE_LINES, separator);
    }


    private void assertCertificateParsed() throws Exception {
        TesterLogValidationFilter f = TesterLogValidationFilter.add(null, "", null,
                "org.apache.catalina.valves.SSLValve");

        EasyMock.verify(mockNext);

        X509Certificate[] certificates = (X509Certificate[]) mockRequest.getAttribute(Globals.CERTIFICATES_ATTR);
        Assert.assertNotNull(certificates);
        Assert.assertEquals(1, certificates.length);
        Assert.assertNotNull(certificates[0]);
        Assert.assertEquals(0, f.getMessageCount());
    }
}