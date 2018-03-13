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


package org.apache.catalina.realm;


import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.TextInputCallback;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.apache.tomcat.util.res.StringManager;

/**
 * <p>Implementation of the JAAS <code>CallbackHandler</code> interface,
 * used to negotiate delivery of the username and credentials that were
 * specified to our constructor.  No interaction with the user is required
 * (or possible).</p>
 *
 * <p>This <code>CallbackHandler</code> will pre-digest the supplied
 * password, if required by the <code>&lt;Realm&gt;</code> element in 
 * <code>server.xml</code>.</p>
 * <p>At present, <code>JAASCallbackHandler</code> knows how to handle callbacks of
 * type <code>javax.security.auth.callback.NameCallback</code> and
 * <code>javax.security.auth.callback.PasswordCallback</code>.</p>
 *
 * @author Craig R. McClanahan
 * @author Andrew R. Jaquith
 */
public class JAASCallbackHandler implements CallbackHandler {

    // ------------------------------------------------------------ Constructor


    /**
     * Construct a callback handler configured with the specified values.
     * Note that if the <code>JAASRealm</code> instance specifies digested passwords,
     * the <code>password</code> parameter will be pre-digested here.
     *
     * @param realm Our associated JAASRealm instance
     * @param username Username to be authenticated with
     * @param password Password to be authenticated with
     */
    public JAASCallbackHandler(JAASRealm realm, String username,
                               String password) {

        super();
        this.realm = realm;
        this.username = username;

        if (realm.hasMessageDigest()) {
            this.password = realm.digest(password);
        }
        else {
            this.password = password;
        }
    }

    
    /**
     * Construct a callback handler for DIGEST authentication.
     *
     * @param realm         Our associated JAASRealm instance
     * @param username      Username to be authenticated with
     * @param password      Password to be authenticated with
     * @param nonce         Server generated nonce
     * @param nc            Nonce count
     * @param cnonce        Client generated nonce
     * @param qop           Quality of protection applied to the message
     * @param realmName     Realm name
     * @param md5a2         Second MD5 digest used to calculate the digest
     *                      MD5(Method + ":" + uri)
     * @param authMethod    The authentication method in use 
     */
    public JAASCallbackHandler(JAASRealm realm, String username,
                               String password, String nonce, String nc,
                               String cnonce, String qop, String realmName,
                               String md5a2, String authMethod) {
        this(realm, username, password);
        this.nonce = nonce;
        this.nc = nc;
        this.cnonce = cnonce;
        this.qop = qop;
        this.realmName = realmName;
        this.md5a2 = md5a2;
        this.authMethod = authMethod;
    }

    // ----------------------------------------------------- Instance Variables

    /**
     * The string manager for this package.
     */
    protected static final StringManager sm =
        StringManager.getManager(Constants.Package);

    /**
     * The password to be authenticated with.
     */
    protected String password = null;


    /**
     * The associated <code>JAASRealm</code> instance.
     */
    protected JAASRealm realm = null;

    /**
     * The username to be authenticated with.
     */
    protected String username = null;

    /**
     * Server generated nonce.
     */
    protected String nonce = null;
    
    /**
     * Nonce count.
     */
    protected String nc = null;
    
    /**
     * Client generated nonce.
     */
    protected String cnonce = null;

    /**
     * Quality of protection applied to the message.
     */
    protected String qop;

    /**
     * Realm name.
     */
    protected String realmName;

    /**
     * Second MD5 digest.
     */
    protected String md5a2;

    /**
     * The authentication method to be used. If null, assume BASIC/FORM.
     */
    protected String authMethod;

    // --------------------------------------------------------- Public Methods


    /**
     * Retrieve the information requested in the provided <code>Callbacks</code>.
     * This implementation only recognizes {@link NameCallback},
     * {@link PasswordCallback} and {@link TextInputCallback}.
     * {@link TextInputCallback} is used to pass the various additional
     * parameters required for DIGEST authentication. 
     *
     * @param callbacks The set of <code>Callback</code>s to be processed
     *
     * @exception IOException if an input/output error occurs
     * @exception UnsupportedCallbackException if the login method requests
     *  an unsupported callback type
     */
    @Override
    public void handle(Callback callbacks[])
        throws IOException, UnsupportedCallbackException {

        for (int i = 0; i < callbacks.length; i++) {

            if (callbacks[i] instanceof NameCallback) {
                if (realm.getContainer().getLogger().isTraceEnabled())
                    realm.getContainer().getLogger().trace(sm.getString("jaasCallback.username", username));
                ((NameCallback) callbacks[i]).setName(username);
            } else if (callbacks[i] instanceof PasswordCallback) {
                final char[] passwordcontents;
                if (password != null) {
                    passwordcontents = password.toCharArray();
                } else {
                    passwordcontents = new char[0];
                }
                ((PasswordCallback) callbacks[i]).setPassword
                    (passwordcontents);
            } else if (callbacks[i] instanceof TextInputCallback) {
                TextInputCallback cb = ((TextInputCallback) callbacks[i]);
                if (cb.getPrompt().equals("nonce")) {
                    cb.setText(nonce);
                } else if (cb.getPrompt().equals("nc")) {
                    cb.setText(nc);
                } else if (cb.getPrompt().equals("cnonce")) {
                    cb.setText(cnonce);
                } else if (cb.getPrompt().equals("qop")) {
                    cb.setText(qop);
                } else if (cb.getPrompt().equals("realmName")) {
                    cb.setText(realmName);
                } else if (cb.getPrompt().equals("md5a2")) {
                    cb.setText(md5a2);
                } else if (cb.getPrompt().equals("authMethod")) {
                    cb.setText(authMethod);
                } else {
                    throw new UnsupportedCallbackException(callbacks[i]);
                }
            } else {
                throw new UnsupportedCallbackException(callbacks[i]);
            }
        }
    }
}
