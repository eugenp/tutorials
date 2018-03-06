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


package org.apache.catalina.ha.session;


import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;

import org.apache.catalina.Realm;
import org.apache.catalina.realm.GenericPrincipal;
import org.apache.tomcat.util.res.StringManager;


/**
 * Generic implementation of <strong>java.security.Principal</strong> that
 * is available for use by <code>Realm</code> implementations.
 * The GenericPrincipal does NOT implement serializable and I didn't want to
 * change that implementation hence I implemented this one instead.
 * @author Filip Hanik
 */
public class SerializablePrincipal  implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private static final org.apache.juli.logging.Log log =
        org.apache.juli.logging.LogFactory.getLog(SerializablePrincipal.class);
    
    /**
     * The string manager for this package.
     */
    protected static final StringManager sm =
        StringManager.getManager(Constants.Package);

    // ----------------------------------------------------------- Constructors

    public SerializablePrincipal() {
        super();
    }
    
    
    /**
     * Construct a new Principal, associated with the specified Realm, for the
     * specified username and password.
     *
     * @param name The username of the user represented by this Principal
     * @param password Credentials used to authenticate this user
     */
    public SerializablePrincipal(String name, String password) {

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
    public SerializablePrincipal(String name, String password,
                            List<String> roles) {
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
     * @param userPrincipal The user principal to be exposed to applications
     */
    public SerializablePrincipal(String name, String password,
                            List<String> roles, Principal userPrincipal) {

        super();
        this.name = name;
        this.password = password;
        if (roles != null) {
            this.roles = new String[roles.size()];
            this.roles = roles.toArray(this.roles);
            if (this.roles.length > 1)
                Arrays.sort(this.roles);
        }
        if (userPrincipal instanceof Serializable) {
            this.userPrincipal = userPrincipal;
        }
    }


    // ------------------------------------------------------------- Properties


    /**
     * The username of the user represented by this Principal.
     */
    protected String name = null;

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
     * The Realm with which this Principal is associated.
     */
    protected transient Realm realm = null;

    public Realm getRealm() {
        return (this.realm);
    }

    public void setRealm(Realm realm) {
        this.realm = realm;
    }


    /**
     * The set of roles associated with this user.
     */
    protected String roles[] = new String[0];

    public String[] getRoles() {
        return (this.roles);
    }


    /**
     * The user principal, if present.
     */
    protected Principal userPrincipal = null;
    
    // --------------------------------------------------------- Public Methods


    /**
     * Return a String representation of this object, which exposes only
     * information that should be public.
     */
    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder("SerializablePrincipal[");
        sb.append(this.name);
        sb.append("]");
        return (sb.toString());

    }

    public static SerializablePrincipal createPrincipal(GenericPrincipal principal)
    {
        if ( principal==null) return null;
        return new SerializablePrincipal(principal.getName(),
                                         principal.getPassword(),
                                         principal.getRoles()!=null?Arrays.asList(principal.getRoles()):null,
                                         principal.getUserPrincipal()!=principal?principal.getUserPrincipal():null);
    }

    public GenericPrincipal getPrincipal()
    {
        return new GenericPrincipal(name, password,
                getRoles()!=null?Arrays.asList(getRoles()):null,
                userPrincipal);
    }
    
    public static GenericPrincipal readPrincipal(ObjectInput in)
            throws IOException, ClassNotFoundException {
        String name = in.readUTF();
        boolean hasPwd = in.readBoolean();
        String pwd = null;
        if ( hasPwd ) pwd = in.readUTF();
        int size = in.readInt();
        String[] roles = new String[size];
        for ( int i=0; i<size; i++ ) roles[i] = in.readUTF();
        Principal userPrincipal = null;
        boolean hasUserPrincipal = in.readBoolean();
        if (hasUserPrincipal) {
            try {
                userPrincipal = (Principal) in.readObject();
            } catch (ClassNotFoundException e) {
                log.error(sm.getString(
                        "serializablePrincipal.readPrincipal.cnfe"), e);
                throw e;
            }
        }
        return new GenericPrincipal(name,pwd,Arrays.asList(roles),
                userPrincipal);
    }
    
    public static void writePrincipal(GenericPrincipal p, ObjectOutput out)
            throws IOException {
        out.writeUTF(p.getName());
        out.writeBoolean(p.getPassword()!=null);
        if ( p.getPassword()!= null ) out.writeUTF(p.getPassword());
        String[] roles = p.getRoles();
        if ( roles == null ) roles = new String[0];
        out.writeInt(roles.length);
        for ( int i=0; i<roles.length; i++ ) out.writeUTF(roles[i]);
        boolean hasUserPrincipal = (p != p.getUserPrincipal() &&
                p.getUserPrincipal() instanceof Serializable);
        out.writeBoolean(hasUserPrincipal);
        if (hasUserPrincipal) out.writeObject(p.getUserPrincipal());
    }


}
