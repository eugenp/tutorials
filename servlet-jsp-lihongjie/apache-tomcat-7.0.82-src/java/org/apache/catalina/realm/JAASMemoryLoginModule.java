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


import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.Map;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.TextInputCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.FailedLoginException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;
import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.Globals;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.apache.tomcat.util.digester.Digester;


/**
 * <p>Implementation of the JAAS <strong>LoginModule</strong> interface,
 * primarily for use in testing <code>JAASRealm</code>.  It utilizes an
 * XML-format data file of username/password/role information identical to
 * that supported by <code>org.apache.catalina.realm.MemoryRealm</code>
 * (except that digested passwords are not supported).</p>
 *
 * <p>This class recognizes the following string-valued options, which are
 * specified in the configuration file (and passed to our constructor in
 * the <code>options</code> argument:</p>
 * <ul>
 * <li><strong>debug</strong> - Set to "true" to get debugging messages
 *     generated to System.out.  The default value is <code>false</code>.</li>
 * <li><strong>pathname</strong> - Relative (to the pathname specified by the
 *     "catalina.base" system property) or absolute pathname to the
 *     XML file containing our user information, in the format supported by
 *     {@link MemoryRealm}.  The default value matches the MemoryRealm
 *     default.</li>
 * </ul>
 *
 * <p><strong>IMPLEMENTATION NOTE</strong> - This class implements
 * <code>Realm</code> only to satisfy the calling requirements of the
 * <code>GenericPrincipal</code> constructor.  It does not actually perform
 * the functionality required of a <code>Realm</code> implementation.</p>
 *
 * @author Craig R. McClanahan
 */
public class JAASMemoryLoginModule extends MemoryRealm implements LoginModule {
    // We need to extend MemoryRealm to avoid class cast

    private static final Log log = LogFactory.getLog(JAASMemoryLoginModule.class);

    // ----------------------------------------------------- Instance Variables


    /**
     * The callback handler responsible for answering our requests.
     */
    protected CallbackHandler callbackHandler = null;


    /**
     * Has our own <code>commit()</code> returned successfully?
     */
    protected boolean committed = false;


    /**
     * The configuration information for this <code>LoginModule</code>.
     */
    protected Map<String,?> options = null;


    /**
     * The absolute or relative pathname to the XML configuration file.
     */
    protected String pathname = "conf/tomcat-users.xml";


    /**
     * The <code>Principal</code> identified by our validation, or
     * <code>null</code> if validation failed.
     */
    protected Principal principal = null;


    /**
     * The state information that is shared with other configured
     * <code>LoginModule</code> instances.
     */
    protected Map<String,?> sharedState = null;


    /**
     * The subject for which we are performing authentication.
     */
    protected Subject subject = null;


    // --------------------------------------------------------- Public Methods

    public JAASMemoryLoginModule() {
        log.debug("MEMORY LOGIN MODULE");
    }

    /**
     * Phase 2 of authenticating a <code>Subject</code> when Phase 1
     * fails.  This method is called if the <code>LoginContext</code>
     * failed somewhere in the overall authentication chain.
     *
     * @return <code>true</code> if this method succeeded, or
     *  <code>false</code> if this <code>LoginModule</code> should be
     *  ignored
     *
     * @exception LoginException if the abort fails
     */
    @Override
    public boolean abort() throws LoginException {

        // If our authentication was not successful, just return false
        if (principal == null)
            return (false);

        // Clean up if overall authentication failed
        if (committed)
            logout();
        else {
            committed = false;
            principal = null;
        }
        log.debug("Abort");
        return (true);

    }


    /**
     * Phase 2 of authenticating a <code>Subject</code> when Phase 1
     * was successful.  This method is called if the <code>LoginContext</code>
     * succeeded in the overall authentication chain.
     *
     * @return <code>true</code> if the authentication succeeded, or
     *  <code>false</code> if this <code>LoginModule</code> should be
     *  ignored
     *
     * @exception LoginException if the commit fails
     */
    @Override
    public boolean commit() throws LoginException {
        log.debug("commit " + principal);

        // If authentication was not successful, just return false
        if (principal == null)
            return (false);

        // Add our Principal to the Subject if needed
        if (!subject.getPrincipals().contains(principal)) {
            subject.getPrincipals().add(principal);
            // Add the roles as additional subjects as per the contract with the
            // JAASRealm
            if (principal instanceof GenericPrincipal) {
                String roles[] = ((GenericPrincipal) principal).getRoles();
                for (int i = 0; i < roles.length; i++) {
                    subject.getPrincipals().add(
                            new GenericPrincipal(roles[i], null, null));
                }
                
            }
        }

        committed = true;
        return (true);

    }

    
    /**
     * Initialize this <code>LoginModule</code> with the specified
     * configuration information.
     *
     * @param subject The <code>Subject</code> to be authenticated
     * @param callbackHandler A <code>CallbackHandler</code> for communicating
     *  with the end user as necessary
     * @param sharedState State information shared with other
     *  <code>LoginModule</code> instances
     * @param options Configuration information for this specific
     *  <code>LoginModule</code> instance
     */
    @Override
    public void initialize(Subject subject, CallbackHandler callbackHandler,
                           Map<String,?> sharedState, Map<String,?> options) {
        log.debug("Init");

        // Save configuration values
        this.subject = subject;
        this.callbackHandler = callbackHandler;
        this.sharedState = sharedState;
        this.options = options;

        // Perform instance-specific initialization
        if (options.get("pathname") != null)
            this.pathname = (String) options.get("pathname");

        // Load our defined Principals
        load();

    }


    /**
     * Phase 1 of authenticating a <code>Subject</code>.
     *
     * @return <code>true</code> if the authentication succeeded, or
     *  <code>false</code> if this <code>LoginModule</code> should be
     *  ignored
     *
     * @exception LoginException if the authentication fails
     */
    @Override
    public boolean login() throws LoginException {

        // Set up our CallbackHandler requests
        if (callbackHandler == null)
            throw new LoginException("No CallbackHandler specified");
        Callback callbacks[] = new Callback[9];
        callbacks[0] = new NameCallback("Username: ");
        callbacks[1] = new PasswordCallback("Password: ", false);
        callbacks[2] = new TextInputCallback("nonce");
        callbacks[3] = new TextInputCallback("nc");
        callbacks[4] = new TextInputCallback("cnonce");
        callbacks[5] = new TextInputCallback("qop");
        callbacks[6] = new TextInputCallback("realmName");
        callbacks[7] = new TextInputCallback("md5a2");
        callbacks[8] = new TextInputCallback("authMethod");

        // Interact with the user to retrieve the username and password
        String username = null;
        String password = null;
        String nonce = null;
        String nc = null;
        String cnonce = null;
        String qop = null;
        String realmName = null;
        String md5a2 = null;
        String authMethod = null;

        try {
            callbackHandler.handle(callbacks);
            username = ((NameCallback) callbacks[0]).getName();
            password =
                new String(((PasswordCallback) callbacks[1]).getPassword());
            nonce = ((TextInputCallback) callbacks[2]).getText();
            nc = ((TextInputCallback) callbacks[3]).getText();
            cnonce = ((TextInputCallback) callbacks[4]).getText();
            qop = ((TextInputCallback) callbacks[5]).getText();
            realmName = ((TextInputCallback) callbacks[6]).getText();
            md5a2 = ((TextInputCallback) callbacks[7]).getText();
            authMethod = ((TextInputCallback) callbacks[8]).getText();
        } catch (IOException e) {
            throw new LoginException(e.toString());
        } catch (UnsupportedCallbackException e) {
            throw new LoginException(e.toString());
        }

        // Validate the username and password we have received
        if (authMethod == null) {
            // BASIC or FORM
            principal = super.authenticate(username, password);
        } else if (authMethod.equals(HttpServletRequest.DIGEST_AUTH)) {
            principal = super.authenticate(username, password, nonce, nc,
                    cnonce, qop, realmName, md5a2);
        } else if (authMethod.equals(HttpServletRequest.CLIENT_CERT_AUTH)) {
            principal = super.getPrincipal(username);
        } else {
            throw new LoginException("Unknown authentication method");
        }

        log.debug("login " + username + " " + principal);

        // Report results based on success or failure
        if (principal != null) {
            return (true);
        } else {
            throw new
                FailedLoginException("Username or password is incorrect");
        }

    }


    /**
     * Log out this user.
     *
     * @return <code>true</code> in all cases because the
     *  <code>LoginModule</code> should not be ignored
     *
     * @exception LoginException if logging out failed
     */
    @Override
    public boolean logout() throws LoginException {

        subject.getPrincipals().remove(principal);
        committed = false;
        principal = null;
        return (true);

    }


    // ---------------------------------------------------------- Realm Methods
    // ------------------------------------------------------ Protected Methods


    /**
     * Load the contents of our configuration file.
     */
    protected void load() {

        // Validate the existence of our configuration file
        File file = new File(pathname);
        if (!file.isAbsolute())
            file = new File(System.getProperty(Globals.CATALINA_BASE_PROP), pathname);
        if (!file.exists() || !file.canRead()) {
            log.warn("Cannot load configuration file " + file.getAbsolutePath());
            return;
        }

        // Load the contents of our configuration file
        Digester digester = new Digester();
        digester.setValidating(false);
        digester.addRuleSet(new MemoryRuleSet());
        try {
            digester.push(this);
            digester.parse(file);
        } catch (Exception e) {
            log.warn("Error processing configuration file " +
                file.getAbsolutePath(), e);
            return;
        } finally {
            digester.reset();
        }

    }
}
