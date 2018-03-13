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


import java.security.Principal;
import java.util.Arrays;
import java.util.List;

import javax.security.auth.login.LoginContext;

import org.ietf.jgss.GSSCredential;


/**
 * Generic implementation of <strong>java.security.Principal</strong> that
 * is available for use by <code>Realm</code> implementations.
 *
 * @author Craig R. McClanahan
 */
public class GenericPrincipal implements Principal {


    // ----------------------------------------------------------- Constructors


    /**
     * Construct a new Principal, associated with the specified Realm, for the
     * specified username and password.
     *
     * @param name The username of the user represented by this Principal
     * @param password Credentials used to authenticate this user
     *
     * @deprecated  Unused
     */
    @Deprecated
    public GenericPrincipal(String name, String password) {

        this(name, password, null);

    }


    /**
     * Construct a new Principal, associated with the specified Realm, for the
     * specified username and password, with the specified role names
     * (as Strings).
     *
     * @param name The username of the user represented by this Principal
     * @param password Credentials used to authenticate this user
     * @param roles List of roles (must be Strings) possessed by this user
     */
    public GenericPrincipal(String name, String password, List<String> roles) {
        this(name, password, roles, null);
    }

    /**
     * Construct a new Principal, associated with the specified Realm, for the
     * specified username and password, with the specified role names
     * (as Strings).
     *
     * @param name The username of the user represented by this Principal
     * @param password Credentials used to authenticate this user
     * @param roles List of roles (must be Strings) possessed by this user
     * @param userPrincipal - the principal to be returned from the request 
     *        getUserPrincipal call if not null; if null, this will be returned
     */
    public GenericPrincipal(String name, String password, List<String> roles,
            Principal userPrincipal) {
        this(name, password, roles, userPrincipal, null);
    }
    
    /**
     * Construct a new Principal, associated with the specified Realm, for the
     * specified username and password, with the specified role names
     * (as Strings).
     *
     * @param name The username of the user represented by this Principal
     * @param password Credentials used to authenticate this user
     * @param roles List of roles (must be Strings) possessed by this user
     * @param userPrincipal - the principal to be returned from the request 
     *        getUserPrincipal call if not null; if null, this will be returned
     * @param loginContext  - If provided, this will be used to log out the user
     *        at the appropriate time
     */
    public GenericPrincipal(String name, String password, List<String> roles,
            Principal userPrincipal, LoginContext loginContext) {
        this(name, password, roles, userPrincipal, loginContext, null);
    }
    
    /**
     * Construct a new Principal, associated with the specified Realm, for the
     * specified username and password, with the specified role names
     * (as Strings).
     *
     * @param name The username of the user represented by this Principal
     * @param password Credentials used to authenticate this user
     * @param roles List of roles (must be Strings) possessed by this user
     * @param userPrincipal - the principal to be returned from the request 
     *        getUserPrincipal call if not null; if null, this will be returned
     * @param loginContext  - If provided, this will be used to log out the user
     *        at the appropriate time
     * @param gssCredential - If provided, the user&apos;s delegated credentials
     */
    public GenericPrincipal(String name, String password, List<String> roles,
            Principal userPrincipal, LoginContext loginContext,
            GSSCredential gssCredential) {
        super();
        this.name = name;
        this.password = password;
        this.userPrincipal = userPrincipal;
        if (roles != null) {
            this.roles = new String[roles.size()];
            this.roles = roles.toArray(this.roles);
            if (this.roles.length > 1)
                Arrays.sort(this.roles);
        }
        this.loginContext = loginContext;
        this.gssCredential = gssCredential;
    }


    // ------------------------------------------------------------- Properties


    /**
     * The username of the user represented by this Principal.
     */
    protected String name = null;

    @Override
    public String getName() {
        return (this.name);
    }


    /**
     * The authentication credentials for the user represented by
     * this Principal.
     */
    protected String password = null;

    public String getPassword() {
        return (this.password);
    }


    /**
     * The set of roles associated with this user.
     */
    protected String roles[] = new String[0];

    public String[] getRoles() {
        return (this.roles);
    }


    /**
     * The authenticated Principal to be exposed to applications.
     */
    protected Principal userPrincipal = null;

    public Principal getUserPrincipal() {
        if (userPrincipal != null) {
            return userPrincipal;
        } else {
            return this;
        }
    }

    
    /**
     * The JAAS LoginContext, if any, used to authenticate this Principal.
     * Kept so we can call logout().
     */
    protected LoginContext loginContext = null;


    /**
     * The user&apos;s delegated credentials.
     */
    protected GSSCredential gssCredential = null;

    public GSSCredential getGssCredential() {
        return this.gssCredential;
    }
    protected void setGssCredential(GSSCredential gssCredential) {
        this.gssCredential = gssCredential;
    }

    // --------------------------------------------------------- Public Methods


    /**
     * Does the user represented by this Principal possess the specified role?
     *
     * @param role Role to be tested
     */
    public boolean hasRole(String role) {

        if("*".equals(role)) // Special 2.4 role meaning everyone
            return true;
        if (role == null)
            return (false);
        return (Arrays.binarySearch(roles, role) >= 0);

    }


    /**
     * Return a String representation of this object, which exposes only
     * information that should be public.
     */
    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder("GenericPrincipal[");
        sb.append(this.name);
        sb.append("(");
        for( int i=0;i<roles.length; i++ ) {
            sb.append( roles[i]).append(",");
        }
        sb.append(")]");
        return (sb.toString());

    }

    
    /**
     * Calls logout, if necessary, on any associated JAASLoginContext. May in
     * the future be extended to cover other logout requirements.
     * 
     * @throws Exception If something goes wrong with the logout. Uses Exception
     *                   to allow for future expansion of this method to cover
     *                   other logout mechanisms that might throw a different
     *                   exception to LoginContext
     * 
     */
    public void logout() throws Exception {
        if (loginContext != null) {
            loginContext.logout();
        }
        if (gssCredential != null) {
            gssCredential.dispose();
        }
    }



}
