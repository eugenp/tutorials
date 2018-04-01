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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.AccountExpiredException;
import javax.security.auth.login.Configuration;
import javax.security.auth.login.CredentialExpiredException;
import javax.security.auth.login.FailedLoginException;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.Container;
import org.apache.catalina.LifecycleException;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.apache.tomcat.util.ExceptionUtils;

/**
 * <p>Implementation of <b>Realm</b> that authenticates users via the <em>Java
 * Authentication and Authorization Service</em> (JAAS).  JAAS support requires
 * either JDK 1.4 (which includes it as part of the standard platform) or
 * JDK 1.3 (with the plug-in <code>jaas.jar</code> file).</p>
 *
 * <p>The value configured for the <code>appName</code> property is passed to
 * the <code>javax.security.auth.login.LoginContext</code> constructor, to
 * specify the <em>application name</em> used to select the set of relevant
 * <code>LoginModules</code> required.</p>
 *
 * <p>The JAAS Specification describes the result of a successful login as a
 * <code>javax.security.auth.Subject</code> instance, which can contain zero
 * or more <code>java.security.Principal</code> objects in the return value
 * of the <code>Subject.getPrincipals()</code> method.  However, it provides
 * no guidance on how to distinguish Principals that describe the individual
 * user (and are thus appropriate to return as the value of
 * request.getUserPrincipal() in a web application) from the Principal(s)
 * that describe the authorized roles for this user.  To maintain as much
 * independence as possible from the underlying <code>LoginMethod</code>
 * implementation executed by JAAS, the following policy is implemented by
 * this Realm:</p>
 * <ul>
 * <li>The JAAS <code>LoginModule</code> is assumed to return a
 *     <code>Subject</code> with at least one <code>Principal</code> instance
 *     representing the user himself or herself, and zero or more separate
 *     <code>Principals</code> representing the security roles authorized
 *     for this user.</li>
 * <li>On the <code>Principal</code> representing the user, the Principal
 *     name is an appropriate value to return via the Servlet API method
 *     <code>HttpServletRequest.getRemoteUser()</code>.</li>
 * <li>On the <code>Principals</code> representing the security roles, the
 *     name is the name of the authorized security role.</li>
 * <li>This Realm will be configured with two lists of fully qualified Java
 *     class names of classes that implement
 *     <code>java.security.Principal</code> - one that identifies class(es)
 *     representing a user, and one that identifies class(es) representing
 *     a security role.</li>
 * <li>As this Realm iterates over the <code>Principals</code> returned by
 *     <code>Subject.getPrincipals()</code>, it will identify the first
 *     <code>Principal</code> that matches the "user classes" list as the
 *     <code>Principal</code> for this user.</li>
 * <li>As this Realm iterates over the <code>Principals</code> returned by
 *     <code>Subject.getPrincipals()</code>, it will accumulate the set of
 *     all <code>Principals</code> matching the "role classes" list as
 *     identifying the security roles for this user.</li>
 * <li>It is a configuration error for the JAAS login method to return a
 *     validated <code>Subject</code> without a <code>Principal</code> that
 *     matches the "user classes" list.</li>
 * <li>By default, the enclosing Container's name serves as the
 *     application name used to obtain the JAAS LoginContext ("Catalina" in
 *     a default installation). Tomcat must be able to find an application
 *     with this name in the JAAS configuration file. Here is a hypothetical
 *     JAAS configuration file entry for a database-oriented login module that uses
 *     a Tomcat-managed JNDI database resource:
 *     <blockquote><pre>Catalina {
org.foobar.auth.DatabaseLoginModule REQUIRED
    JNDI_RESOURCE=jdbc/AuthDB
  USER_TABLE=users
  USER_ID_COLUMN=id
  USER_NAME_COLUMN=name
  USER_CREDENTIAL_COLUMN=password
  ROLE_TABLE=roles
  ROLE_NAME_COLUMN=name
  PRINCIPAL_FACTORY=org.foobar.auth.impl.SimplePrincipalFactory;
};</pre></blockquote></li>
 * <li>To set the JAAS configuration file
 *     location, set the <code>CATALINA_OPTS</code> environment variable
 *     similar to the following:
<blockquote><code>CATALINA_OPTS="-Djava.security.auth.login.config=$CATALINA_HOME/conf/jaas.config"</code></blockquote>
 * </li>
 * <li>As part of the login process, JAASRealm registers its own <code>CallbackHandler</code>,
 *     called (unsurprisingly) <code>JAASCallbackHandler</code>. This handler supplies the
 *     HTTP requests's username and credentials to the user-supplied <code>LoginModule</code></li>
 * <li>As with other <code>Realm</code> implementations, digested passwords are supported if
 *     the <code>&lt;Realm&gt;</code> element in <code>server.xml</code> contains a
 *     <code>digest</code> attribute; <code>JAASCallbackHandler</code> will digest the password
 *     prior to passing it back to the <code>LoginModule</code></li>
 * </ul>
 *
* @author Craig R. McClanahan
* @author Yoav Shapira
 */
public class JAASRealm extends RealmBase {

    private static final Log log = LogFactory.getLog(JAASRealm.class);

    // ----------------------------------------------------- Instance Variables


    /**
     * The application name passed to the JAAS <code>LoginContext</code>,
     * which uses it to select the set of relevant <code>LoginModule</code>s.
     */
    protected String appName = null;


    /**
     * Descriptive information about this <code>Realm</code> implementation.
     */
    protected static final String info =
        "org.apache.catalina.realm.JAASRealm/1.0";


    /**
     * Descriptive information about this <code>Realm</code> implementation.
     */
    protected static final String name = "JAASRealm";


    /**
     * The list of role class names, split out for easy processing.
     */
    protected List<String> roleClasses = new ArrayList<String>();


    /**
     * The set of user class names, split out for easy processing.
     */
    protected List<String> userClasses = new ArrayList<String>();


    /**
     * Whether to use context ClassLoader or default ClassLoader.
     * True means use context ClassLoader, and True is the default
     * value.
     */
    protected boolean useContextClassLoader = true;


    /**
     * Path to find a JAAS configuration file, if not set global JVM JAAS
     * configuration will be used.
     */
    protected String configFile;

    protected Configuration jaasConfiguration;
    protected volatile boolean jaasConfigurationLoaded = false;


    // ------------------------------------------------------------- Properties

    /**
     * Getter for the <code>configfile</code> member variable.
     */
    public String getConfigFile() {
        return configFile;
    }

    /**
     * Setter for the <code>configfile</code> member variable.
     */
    public void setConfigFile(String configFile) {
        this.configFile = configFile;
    }

    /**
     * setter for the <code>appName</code> member variable
     */
    public void setAppName(String name) {
        appName = name;
    }

    /**
     * getter for the <code>appName</code> member variable
     */
    public String getAppName() {
        return appName;
    }

    /**
     * Sets whether to use the context or default ClassLoader.
     * True means use context ClassLoader.
     *
     * @param useContext True means use context ClassLoader
     */
    public void setUseContextClassLoader(boolean useContext) {
      useContextClassLoader = useContext;
      log.info("Setting useContextClassLoader = " + useContext);
    }

    /**
     * Returns whether to use the context or default ClassLoader.
     * True means to use the context ClassLoader.
     *
     * @return The value of useContextClassLoader
     */
    public boolean isUseContextClassLoader() {
        return useContextClassLoader;
    }

    @Override
    public void setContainer(Container container) {
        super.setContainer(container);

        if( appName==null  ) {
            String name = container.getName();
            if (!name.startsWith("/")) {
                name = "/" + name;
            }
            name = makeLegalForJAAS(name);

            appName=name;

            log.info("Set JAAS app name " + appName);
        }
    }

     /**
      * Comma-delimited list of <code>java.security.Principal</code> classes
      * that represent security roles.
      */
     protected String roleClassNames = null;

     public String getRoleClassNames() {
         return (this.roleClassNames);
     }

     /**
      * Sets the list of comma-delimited classes that represent roles. The
      * classes in the list must implement <code>java.security.Principal</code>.
      * The supplied list of classes will be parsed when {@link #start()} is
      * called.
      */
     public void setRoleClassNames(String roleClassNames) {
         this.roleClassNames = roleClassNames;
     }

     /**
      * Parses a comma-delimited list of class names, and store the class names
      * in the provided List. Each class must implement
      * <code>java.security.Principal</code>.
      *
      * @param classNamesString a comma-delimited list of fully qualified class names.
      * @param classNamesList the list in which the class names will be stored.
      *        The list is cleared before being populated.
      */
     protected void parseClassNames(String classNamesString, List<String> classNamesList) {
         classNamesList.clear();
         if (classNamesString == null) return;

         ClassLoader loader = this.getClass().getClassLoader();
         if (isUseContextClassLoader())
             loader = Thread.currentThread().getContextClassLoader();

         String[] classNames = classNamesString.split("[ ]*,[ ]*");
         for (int i=0; i<classNames.length; i++) {
             if (classNames[i].length()==0) continue;
             try {
                 Class<?> principalClass = Class.forName(classNames[i], false,
                         loader);
                 if (Principal.class.isAssignableFrom(principalClass)) {
                     classNamesList.add(classNames[i]);
                 } else {
                     log.error("Class "+classNames[i]+" is not implementing "+
                               "java.security.Principal! Class not added.");
                 }
             } catch (ClassNotFoundException e) {
                 log.error("Class "+classNames[i]+" not found! Class not added.");
             }
         }
     }

     /**
      * Comma-delimited list of <code>java.security.Principal</code> classes
      * that represent individual users.
      */
     protected String userClassNames = null;

     public String getUserClassNames() {
         return (this.userClassNames);
     }

     /**
      * Sets the list of comma-delimited classes that represent individual
      * users. The classes in the list must implement
      * <code>java.security.Principal</code>. The supplied list of classes will
      * be parsed when {@link #start()} is called.
      */
    public void setUserClassNames(String userClassNames) {
        this.userClassNames = userClassNames;
    }

    /**
     * Return descriptive information about this Realm implementation and
     * the corresponding version number, in the format
     * <code>&lt;description&gt;/&lt;version&gt;</code>.
     */
    @Override
    public String getInfo() {

        return info;

    }


    // --------------------------------------------------------- Public Methods


    /**
     * Return the <code>Principal</code> associated with the specified username
     * and credentials, if there is one; otherwise return <code>null</code>.
     *
     * @param username Username of the <code>Principal</code> to look up
     * @param credentials Password or other credentials to use in
     *  authenticating this username
     */
    @Override
    public Principal authenticate(String username, String credentials) {
        return authenticate(username,
                new JAASCallbackHandler(this, username, credentials));
    }


    /**
     * Return the <code>Principal</code> associated with the specified username
     * and digest, if there is one; otherwise return <code>null</code>.
     *
     * @param username      Username of the <code>Principal</code> to look up
     * @param clientDigest  Digest to use in authenticating this username
     * @param nonce         Server generated nonce
     * @param nc            Nonce count
     * @param cnonce        Client generated nonce
     * @param qop           Quality of protection applied to the message
     * @param realmName     Realm name
     * @param md5a2         Second MD5 digest used to calculate the digest
     *                          MD5(Method + ":" + uri)
     */
    @Override
    public Principal authenticate(String username, String clientDigest,
            String nonce, String nc, String cnonce, String qop,
            String realmName, String md5a2) {
        return authenticate(username,
                new JAASCallbackHandler(this, username, clientDigest, nonce,
                        nc, cnonce, qop, realmName, md5a2,
                        HttpServletRequest.DIGEST_AUTH));
    }


    // -------------------------------------------------------- Package Methods


    // ------------------------------------------------------ Protected Methods


    /**
     * Perform the actual JAAS authentication
     */
    protected Principal authenticate(String username,
            CallbackHandler callbackHandler) {

        // Establish a LoginContext to use for authentication
        try {
        LoginContext loginContext = null;
        if( appName==null ) appName="Tomcat";

        if( log.isDebugEnabled())
            log.debug(sm.getString("jaasRealm.beginLogin", username, appName));

        // What if the LoginModule is in the container class loader ?
        ClassLoader ocl = null;

        if (!isUseContextClassLoader()) {
          ocl = Thread.currentThread().getContextClassLoader();
          Thread.currentThread().setContextClassLoader(
                  this.getClass().getClassLoader());
        }

        try {
            Configuration config = getConfig();
            loginContext = new LoginContext(
                    appName, null, callbackHandler, config);
        } catch (Throwable e) {
            ExceptionUtils.handleThrowable(e);
            log.error(sm.getString("jaasRealm.unexpectedError"), e);
            return (null);
        } finally {
            if(!isUseContextClassLoader()) {
              Thread.currentThread().setContextClassLoader(ocl);
            }
        }

        if( log.isDebugEnabled())
            log.debug("Login context created " + username);

        // Negotiate a login via this LoginContext
        Subject subject = null;
        try {
            loginContext.login();
            subject = loginContext.getSubject();
            if (subject == null) {
                if( log.isDebugEnabled())
                    log.debug(sm.getString("jaasRealm.failedLogin", username));
                return (null);
            }
        } catch (AccountExpiredException e) {
            if (log.isDebugEnabled())
                log.debug(sm.getString("jaasRealm.accountExpired", username));
            return (null);
        } catch (CredentialExpiredException e) {
            if (log.isDebugEnabled())
                log.debug(sm.getString("jaasRealm.credentialExpired", username));
            return (null);
        } catch (FailedLoginException e) {
            if (log.isDebugEnabled())
                log.debug(sm.getString("jaasRealm.failedLogin", username));
            return (null);
        } catch (LoginException e) {
            log.warn(sm.getString("jaasRealm.loginException", username), e);
            return (null);
        } catch (Throwable e) {
            ExceptionUtils.handleThrowable(e);
            log.error(sm.getString("jaasRealm.unexpectedError"), e);
            return (null);
        }

        if( log.isDebugEnabled())
            log.debug(sm.getString("jaasRealm.loginContextCreated", username));

        // Return the appropriate Principal for this authenticated Subject
        Principal principal = createPrincipal(username, subject, loginContext);
        if (principal == null) {
            log.debug(sm.getString("jaasRealm.authenticateFailure", username));
            return (null);
        }
        if (log.isDebugEnabled()) {
            log.debug(sm.getString("jaasRealm.authenticateSuccess", username));
        }

        return (principal);
        } catch( Throwable t) {
            log.error( "error ", t);
            return null;
        }
    }

    /**
     * Return a short name for this <code>Realm</code> implementation.
     */
    @Override
    protected String getName() {

        return (name);

    }


    /**
     * Return the password associated with the given principal's user name. This
     * always returns null as the JAASRealm has no way of obtaining this
     * information.
     */
    @Override
    protected String getPassword(String username) {

        return (null);

    }


    /**
     * Return the <code>Principal</code> associated with the given user name.
     */
    @Override
    protected Principal getPrincipal(String username) {

        return authenticate(username,
                new JAASCallbackHandler(this, username, null, null, null, null,
                        null, null, null, HttpServletRequest.CLIENT_CERT_AUTH));

    }


    /**
     * Identify and return a <code>java.security.Principal</code> instance
     * representing the authenticated user for the specified <code>Subject</code>.
     * The Principal is constructed by scanning the list of Principals returned
     * by the JAASLoginModule. The first <code>Principal</code> object that matches
     * one of the class names supplied as a "user class" is the user Principal.
     * This object is returned to the caller.
     * Any remaining principal objects returned by the LoginModules are mapped to
     * roles, but only if their respective classes match one of the "role class" classes.
     * If a user Principal cannot be constructed, return <code>null</code>.
     * @param subject The <code>Subject</code> representing the logged-in user
     * @param loginContext Associated with the Principal so
     *                     {@link LoginContext#logout()} can be called later
     */
    protected Principal createPrincipal(String username, Subject subject,
            LoginContext loginContext) {
        // Prepare to scan the Principals for this Subject

        List<String> roles = new ArrayList<String>();
        Principal userPrincipal = null;

        // Scan the Principals for this Subject
        Iterator<Principal> principals = subject.getPrincipals().iterator();
        while (principals.hasNext()) {
            Principal principal = principals.next();

            String principalClass = principal.getClass().getName();

            if( log.isDebugEnabled() ) {
                log.debug(sm.getString("jaasRealm.checkPrincipal", principal, principalClass));
            }

            if (userPrincipal == null && userClasses.contains(principalClass)) {
                userPrincipal = principal;
                if( log.isDebugEnabled() ) {
                    log.debug(sm.getString("jaasRealm.userPrincipalSuccess", principal.getName()));
                }
            }

            if (roleClasses.contains(principalClass)) {
                roles.add(principal.getName());
                if( log.isDebugEnabled() ) {
                    log.debug(sm.getString("jaasRealm.rolePrincipalAdd", principal.getName()));
                }
            }
        }

        // Print failure message if needed
        if (userPrincipal == null) {
            if (log.isDebugEnabled()) {
                log.debug(sm.getString("jaasRealm.userPrincipalFailure"));
                log.debug(sm.getString("jaasRealm.rolePrincipalFailure"));
            }
        } else {
            if (roles.size() == 0) {
                if (log.isDebugEnabled()) {
                    log.debug(sm.getString("jaasRealm.rolePrincipalFailure"));
                }
            }
        }

        // Return the resulting Principal for our authenticated user
        return new GenericPrincipal(username, null, roles, userPrincipal,
                loginContext);
    }

     /**
      * Ensure the given name is legal for JAAS configuration.
      * Added for Bugzilla 30869, made protected for easy customization
      * in case my implementation is insufficient, which I think is
      * very likely.
      *
      * @param src The name to validate
      * @return A string that's a valid JAAS realm name
      */
     protected String makeLegalForJAAS(final String src) {
         String result = src;

         // Default name is "other" per JAAS spec
         if(result == null) {
             result = "other";
         }

         // Strip leading slash if present, as Sun JAAS impl
         // barfs on it (see Bugzilla 30869 bug report).
         if(result.startsWith("/")) {
             result = result.substring(1);
         }

         return result;
     }


    // ------------------------------------------------------ Lifecycle Methods


     /**
      * Prepare for the beginning of active use of the public methods of this
      * component and implement the requirements of
      * {@link org.apache.catalina.util.LifecycleBase#startInternal()}.
      *
      * @exception LifecycleException if this component detects a fatal error
      *  that prevents this component from being used
      */
     @Override
     protected void startInternal() throws LifecycleException {

        // These need to be called after loading configuration, in case
        // useContextClassLoader appears after them in xml config
        parseClassNames(userClassNames, userClasses);
        parseClassNames(roleClassNames, roleClasses);

        super.startInternal();
     }


    /**
     * Load custom JAAS Configuration
     */
    protected Configuration getConfig() {
        try {
            if (jaasConfigurationLoaded) {
                return jaasConfiguration;
            }
            synchronized (this) {
                if (configFile == null) {
                    jaasConfigurationLoaded = true;
                    return null;
                }
                URL resource = Thread.currentThread().getContextClassLoader().
                        getResource(configFile);
                URI uri = resource.toURI();
                @SuppressWarnings("unchecked")
                Class<Configuration> sunConfigFile = (Class<Configuration>)
                        Class.forName("com.sun.security.auth.login.ConfigFile");
                Constructor<Configuration> constructor =
                        sunConfigFile.getConstructor(URI.class);
                Configuration config = constructor.newInstance(uri);
                this.jaasConfiguration = config;
                this.jaasConfigurationLoaded = true;
                return this.jaasConfiguration;
            }
        } catch (URISyntaxException ex) {
            throw new RuntimeException(ex);
        } catch (NoSuchMethodException ex) {
            throw new RuntimeException(ex);
        } catch (SecurityException ex) {
            throw new RuntimeException(ex);
        } catch (InstantiationException ex) {
            throw new RuntimeException(ex);
        } catch (IllegalAccessException ex) {
            throw new RuntimeException(ex);
        } catch (IllegalArgumentException ex) {
            throw new RuntimeException(ex);
        } catch (InvocationTargetException ex) {
            throw new RuntimeException(ex.getCause());
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }

    }
}
