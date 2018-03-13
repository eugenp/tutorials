/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.apache.tomcat.jni;

/** SSL
 *
 * @author Mladen Turk
 */

public final class SSL {

    /*
     * Type definitions mostly from mod_ssl
     */
    public static final int UNSET            = -1;
    /*
     * Define the certificate algorithm types
     */
    public static final int SSL_ALGO_UNKNOWN = 0;
    public static final int SSL_ALGO_RSA     = (1<<0);
    public static final int SSL_ALGO_DSA     = (1<<1);
    public static final int SSL_ALGO_ALL     = (SSL_ALGO_RSA|SSL_ALGO_DSA);

    public static final int SSL_AIDX_RSA     = 0;
    public static final int SSL_AIDX_DSA     = 1;
    public static final int SSL_AIDX_MAX     = 2;
    /*
     * Define IDs for the temporary RSA keys and DH params
     */

    public static final int SSL_TMP_KEY_RSA_512  = 0;
    public static final int SSL_TMP_KEY_RSA_1024 = 1;
    public static final int SSL_TMP_KEY_RSA_2048 = 2;
    public static final int SSL_TMP_KEY_RSA_4096 = 3;
    public static final int SSL_TMP_KEY_DH_512   = 4;
    public static final int SSL_TMP_KEY_DH_1024  = 5;
    public static final int SSL_TMP_KEY_DH_2048  = 6;
    public static final int SSL_TMP_KEY_DH_4096  = 7;
    public static final int SSL_TMP_KEY_MAX      = 8;

    /*
     * Define the SSL options
     */
    public static final int SSL_OPT_NONE           = 0;
    public static final int SSL_OPT_RELSET         = (1<<0);
    public static final int SSL_OPT_STDENVVARS     = (1<<1);
    public static final int SSL_OPT_EXPORTCERTDATA = (1<<3);
    public static final int SSL_OPT_FAKEBASICAUTH  = (1<<4);
    public static final int SSL_OPT_STRICTREQUIRE  = (1<<5);
    public static final int SSL_OPT_OPTRENEGOTIATE = (1<<6);
    public static final int SSL_OPT_ALL            = (SSL_OPT_STDENVVARS|SSL_OPT_EXPORTCERTDATA|SSL_OPT_FAKEBASICAUTH|SSL_OPT_STRICTREQUIRE|SSL_OPT_OPTRENEGOTIATE);

    /*
     * Define the SSL Protocol options
     */
    public static final int SSL_PROTOCOL_NONE  = 0;
    public static final int SSL_PROTOCOL_SSLV2 = (1<<0);
    public static final int SSL_PROTOCOL_SSLV3 = (1<<1);
    public static final int SSL_PROTOCOL_TLSV1 = (1<<2);
    public static final int SSL_PROTOCOL_TLSV1_1 = (1<<3);
    public static final int SSL_PROTOCOL_TLSV1_2 = (1<<4);
    public static final int SSL_PROTOCOL_ALL   = (SSL_PROTOCOL_TLSV1 | SSL_PROTOCOL_TLSV1_1 | SSL_PROTOCOL_TLSV1_2);

    /*
     * Define the SSL verify levels
     */
    public static final int SSL_CVERIFY_UNSET          = UNSET;
    public static final int SSL_CVERIFY_NONE           = 0;
    public static final int SSL_CVERIFY_OPTIONAL       = 1;
    public static final int SSL_CVERIFY_REQUIRE        = 2;
    public static final int SSL_CVERIFY_OPTIONAL_NO_CA = 3;

    /* Use either SSL_VERIFY_NONE or SSL_VERIFY_PEER, the last 2 options
     * are 'ored' with SSL_VERIFY_PEER if they are desired
     */
    public static final int SSL_VERIFY_NONE                 = 0;
    public static final int SSL_VERIFY_PEER                 = 1;
    public static final int SSL_VERIFY_FAIL_IF_NO_PEER_CERT = 2;
    public static final int SSL_VERIFY_CLIENT_ONCE          = 4;
    public static final int SSL_VERIFY_PEER_STRICT          = (SSL_VERIFY_PEER|SSL_VERIFY_FAIL_IF_NO_PEER_CERT);

    public static final int SSL_OP_MICROSOFT_SESS_ID_BUG            = 0x00000001;
    public static final int SSL_OP_NETSCAPE_CHALLENGE_BUG           = 0x00000002;
    public static final int SSL_OP_NETSCAPE_REUSE_CIPHER_CHANGE_BUG = 0x00000008;
    public static final int SSL_OP_SSLREF2_REUSE_CERT_TYPE_BUG      = 0x00000010;
    public static final int SSL_OP_MICROSOFT_BIG_SSLV3_BUFFER       = 0x00000020;
    public static final int SSL_OP_MSIE_SSLV2_RSA_PADDING           = 0x00000040;
    public static final int SSL_OP_SSLEAY_080_CLIENT_DH_BUG         = 0x00000080;
    public static final int SSL_OP_TLS_D5_BUG                       = 0x00000100;
    public static final int SSL_OP_TLS_BLOCK_PADDING_BUG            = 0x00000200;

    /* Disable SSL 3.0/TLS 1.0 CBC vulnerability workaround that was added
     * in OpenSSL 0.9.6d.  Usually (depending on the application protocol)
     * the workaround is not needed.  Unfortunately some broken SSL/TLS
     * implementations cannot handle it at all, which is why we include
     * it in SSL_OP_ALL. */
    public static final int SSL_OP_DONT_INSERT_EMPTY_FRAGMENTS      = 0x00000800;

    /* SSL_OP_ALL: various bug workarounds that should be rather harmless.
     *             This used to be 0x000FFFFFL before 0.9.7. */
    public static final int SSL_OP_ALL                              = 0x00000FFF;
    /* As server, disallow session resumption on renegotiation */
    public static final int SSL_OP_NO_SESSION_RESUMPTION_ON_RENEGOTIATION = 0x00010000;
    /* Don't use compression even if supported */
    public static final int SSL_OP_NO_COMPRESSION                         = 0x00020000;
    /* Permit unsafe legacy renegotiation */
    public static final int SSL_OP_ALLOW_UNSAFE_LEGACY_RENEGOTIATION      = 0x00040000;
    /* If set, always create a new key when using tmp_eddh parameters */
    public static final int SSL_OP_SINGLE_ECDH_USE                  = 0x00080000;
    /* If set, always create a new key when using tmp_dh parameters */
    public static final int SSL_OP_SINGLE_DH_USE                    = 0x00100000;
    /* Set to always use the tmp_rsa key when doing RSA operations,
     * even when this violates protocol specs */
    public static final int SSL_OP_EPHEMERAL_RSA                    = 0x00200000;
    /* Set on servers to choose the cipher according to the server's
     * preferences */
    public static final int SSL_OP_CIPHER_SERVER_PREFERENCE         = 0x00400000;
    /* If set, a server will allow a client to issue a SSLv3.0 version number
     * as latest version supported in the premaster secret, even when TLSv1.0
     * (version 3.1) was announced in the client hello. Normally this is
     * forbidden to prevent version rollback attacks. */
    public static final int SSL_OP_TLS_ROLLBACK_BUG                 = 0x00800000;

    public static final int SSL_OP_NO_SSLv2                         = 0x01000000;
    public static final int SSL_OP_NO_SSLv3                         = 0x02000000;
    public static final int SSL_OP_NO_TLSv1                         = 0x04000000;
    public static final int SSL_OP_NO_TLSv1_2                       = 0x08000000;
    public static final int SSL_OP_NO_TLSv1_1                       = 0x10000000;

    public static final int SSL_OP_NO_TICKET                        = 0x00004000;

    // SSL_OP_PKCS1_CHECK_1 and SSL_OP_PKCS1_CHECK_2 flags are unsupported
    // in the current version of OpenSSL library. See ssl.h changes in commit
    // 7409d7ad517650db332ae528915a570e4e0ab88b (30 Apr 2011) of OpenSSL.
    /**
     * @deprecated Unsupported in the current version of OpenSSL
     */
    @Deprecated
    public static final int SSL_OP_PKCS1_CHECK_1                    = 0x08000000;
    /**
     * @deprecated Unsupported in the current version of OpenSSL
     */
    @Deprecated
    public static final int SSL_OP_PKCS1_CHECK_2                    = 0x10000000;
    public static final int SSL_OP_NETSCAPE_CA_DN_BUG               = 0x20000000;
    public static final int SSL_OP_NETSCAPE_DEMO_CIPHER_CHANGE_BUG  = 0x40000000;

    public static final int SSL_CRT_FORMAT_UNDEF    = 0;
    public static final int SSL_CRT_FORMAT_ASN1     = 1;
    public static final int SSL_CRT_FORMAT_TEXT     = 2;
    public static final int SSL_CRT_FORMAT_PEM      = 3;
    public static final int SSL_CRT_FORMAT_NETSCAPE = 4;
    public static final int SSL_CRT_FORMAT_PKCS12   = 5;
    public static final int SSL_CRT_FORMAT_SMIME    = 6;
    public static final int SSL_CRT_FORMAT_ENGINE   = 7;

    public static final int SSL_MODE_CLIENT         = 0;
    public static final int SSL_MODE_SERVER         = 1;
    public static final int SSL_MODE_COMBINED       = 2;

    public static final int SSL_SHUTDOWN_TYPE_UNSET    = 0;
    public static final int SSL_SHUTDOWN_TYPE_STANDARD = 1;
    public static final int SSL_SHUTDOWN_TYPE_UNCLEAN  = 2;
    public static final int SSL_SHUTDOWN_TYPE_ACCURATE = 3;

    public static final int SSL_INFO_SESSION_ID                = 0x0001;
    public static final int SSL_INFO_CIPHER                    = 0x0002;
    public static final int SSL_INFO_CIPHER_USEKEYSIZE         = 0x0003;
    public static final int SSL_INFO_CIPHER_ALGKEYSIZE         = 0x0004;
    public static final int SSL_INFO_CIPHER_VERSION            = 0x0005;
    public static final int SSL_INFO_CIPHER_DESCRIPTION        = 0x0006;
    public static final int SSL_INFO_PROTOCOL                  = 0x0007;

    /* To obtain the CountryName of the Client Certificate Issuer
     * use the SSL_INFO_CLIENT_I_DN + SSL_INFO_DN_COUNTRYNAME
     */
    public static final int SSL_INFO_CLIENT_S_DN               = 0x0010;
    public static final int SSL_INFO_CLIENT_I_DN               = 0x0020;
    public static final int SSL_INFO_SERVER_S_DN               = 0x0040;
    public static final int SSL_INFO_SERVER_I_DN               = 0x0080;

    public static final int SSL_INFO_DN_COUNTRYNAME            = 0x0001;
    public static final int SSL_INFO_DN_STATEORPROVINCENAME    = 0x0002;
    public static final int SSL_INFO_DN_LOCALITYNAME           = 0x0003;
    public static final int SSL_INFO_DN_ORGANIZATIONNAME       = 0x0004;
    public static final int SSL_INFO_DN_ORGANIZATIONALUNITNAME = 0x0005;
    public static final int SSL_INFO_DN_COMMONNAME             = 0x0006;
    public static final int SSL_INFO_DN_TITLE                  = 0x0007;
    public static final int SSL_INFO_DN_INITIALS               = 0x0008;
    public static final int SSL_INFO_DN_GIVENNAME              = 0x0009;
    public static final int SSL_INFO_DN_SURNAME                = 0x000A;
    public static final int SSL_INFO_DN_DESCRIPTION            = 0x000B;
    public static final int SSL_INFO_DN_UNIQUEIDENTIFIER       = 0x000C;
    public static final int SSL_INFO_DN_EMAILADDRESS           = 0x000D;

    public static final int SSL_INFO_CLIENT_M_VERSION          = 0x0101;
    public static final int SSL_INFO_CLIENT_M_SERIAL           = 0x0102;
    public static final int SSL_INFO_CLIENT_V_START            = 0x0103;
    public static final int SSL_INFO_CLIENT_V_END              = 0x0104;
    public static final int SSL_INFO_CLIENT_A_SIG              = 0x0105;
    public static final int SSL_INFO_CLIENT_A_KEY              = 0x0106;
    public static final int SSL_INFO_CLIENT_CERT               = 0x0107;
    public static final int SSL_INFO_CLIENT_V_REMAIN           = 0x0108;

    public static final int SSL_INFO_SERVER_M_VERSION          = 0x0201;
    public static final int SSL_INFO_SERVER_M_SERIAL           = 0x0202;
    public static final int SSL_INFO_SERVER_V_START            = 0x0203;
    public static final int SSL_INFO_SERVER_V_END              = 0x0204;
    public static final int SSL_INFO_SERVER_A_SIG              = 0x0205;
    public static final int SSL_INFO_SERVER_A_KEY              = 0x0206;
    public static final int SSL_INFO_SERVER_CERT               = 0x0207;
    /* Return client certificate chain.
     * Add certificate chain number to that flag (0 ... verify depth)
     */
    public static final int SSL_INFO_CLIENT_CERT_CHAIN         = 0x0400;
    /* Return OpenSSL version number (compile time version, if version < 1.1.0) */
    public static native int version();

    /* Return OpenSSL version string (run time version) */
    public static native String versionString();

    /**
     * Initialize OpenSSL support.
     * This function needs to be called once for the
     * lifetime of JVM. Library.init() has to be called before.
     * @param engine Support for external a Crypto Device ("engine"),
     *                usually
     * a hardware accelerator card for crypto operations.
     * @return APR status code
     */
    public static native int initialize(String engine);

    /**
     * Get the status of FIPS Mode.
     *
     * @return FIPS_mode return code. It is <code>0</code> if OpenSSL is not
     *  in FIPS mode, <code>1</code> if OpenSSL is in FIPS Mode.
     * @throws Exception If tcnative was not compiled with FIPS Mode available.
     * @see <a href="http://wiki.openssl.org/index.php/FIPS_mode%28%29">OpenSSL method FIPS_mode()</a>
     */
    public static native int fipsModeGet() throws Exception;

    /**
     * Enable/Disable FIPS Mode.
     *
     * @param mode 1 - enable, 0 - disable
     *
     * @return FIPS_mode_set return code
     * @throws Exception If tcnative was not compiled with FIPS Mode available,
     *  or if {@code FIPS_mode_set()} call returned an error value.
     * @see <a href="http://wiki.openssl.org/index.php/FIPS_mode_set%28%29">OpenSSL method FIPS_mode_set()</a>
     */
    public static native int fipsModeSet(int mode) throws Exception;

    /**
     * Add content of the file to the PRNG
     * @param filename Filename containing random data.
     *        If null the default file will be tested.
     *        The seed file is $RANDFILE if that environment variable is
     *        set, $HOME/.rnd otherwise.
     *        In case both files are unavailable builtin
     *        random seed generator is used.
     */
    public static native boolean randLoad(String filename);

    /**
     * Writes a number of random bytes (currently 1024) to
     * file <code>filename</code> which can be used to initialize the PRNG
     * by calling randLoad in a later session.
     * @param filename Filename to save the data
     */
    public static native boolean randSave(String filename);

    /**
     * Creates random data to filename
     * @param filename Filename to save the data
     * @param len The length of random sequence in bytes
     * @param base64 Output the data in Base64 encoded format
     */
    public static native boolean randMake(String filename, int len,
                                          boolean base64);

    /**
     * Sets global random filename.
     * @param filename Filename to use.
     *        If set it will be used for SSL initialization
     *        and all contexts where explicitly not set.
     */
    public static native void randSet(String filename);

    /**
     * Initialize new BIO
     * @param pool The pool to use.
     * @param callback BIOCallback to use
     * @return New BIO handle
     */
     public static native long newBIO(long pool, BIOCallback callback)
            throws Exception;

    /**
     * Close BIO and dereference callback object
     * @param bio BIO to close and destroy.
     * @return APR Status code
     */
     public static native int closeBIO(long bio);

    /**
     * Set global Password callback for obtaining passwords.
     * @param callback PasswordCallback implementation to use.
     */
     public static native void setPasswordCallback(PasswordCallback callback);

    /**
     * Set global Password for decrypting certificates and keys.
     * @param password Password to use.
     */
     public static native void setPassword(String password);

    /**
     * Generate temporary RSA key.
     * <br>
     * Index can be one of:
     * <PRE>
     * SSL_TMP_KEY_RSA_512
     * SSL_TMP_KEY_RSA_1024
     * SSL_TMP_KEY_RSA_2048
     * SSL_TMP_KEY_RSA_4096
     * </PRE>
     * By default 512 and 1024 keys are generated on startup.
     * You can use a low priority thread to generate them on the fly.
     * @param idx temporary key index.
     */
    /**
     * @deprecated Only useful in combination with EXPORT Cipher
     */
    @Deprecated
    public static native boolean generateRSATempKey(int idx);

    /**
     * Load temporary DSA key from file
     * <br>
     * Index can be one of:
     * <PRE>
     * SSL_TMP_KEY_DH_512
     * SSL_TMP_KEY_DH_1024
     * SSL_TMP_KEY_DH_2048
     * SSL_TMP_KEY_DH_4096
     * </PRE>
     * @param idx temporary key index.
     * @param file File containing DH params.
     */
    /**
     * @deprecated Now automatically loaded from certificate file
     */
    @Deprecated
    public static native boolean loadDSATempKey(int idx, String file);

    /**
     * Return last SSL error string
     */
    public static native String getLastError();

    /**
     * Return true if all the requested SSL_OP_* are supported by OpenSSL.
     * 
     * <i>Note that for versions of tcnative &lt; 1.1.25, this method will
     * return <code>true</code> if and only if <code>op</code>=
     * {@link #SSL_OP_ALLOW_UNSAFE_LEGACY_RENEGOTIATION} and tcnative
     * supports that flag.</i>
     *
     * @param op Bitwise-OR of all SSL_OP_* to test.
     * 
     * @return true if all SSL_OP_* are supported by OpenSSL library.
     */
    public static native boolean hasOp(int op);
}
