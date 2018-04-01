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


package org.apache.naming;

import java.util.Hashtable;

/**
 * Handles the access control on the JNDI contexts.
 *
 * @author Remy Maucherat
 */
public class ContextAccessController {


    // -------------------------------------------------------------- Variables


    /**
     * Catalina context names on which writing is not allowed.
     */
    private static Hashtable<Object,Object> readOnlyContexts =
        new Hashtable<Object,Object>();


    /**
     * Security tokens repository.
     */
    private static Hashtable<Object,Object> securityTokens =
        new Hashtable<Object,Object>();


    // --------------------------------------------------------- Public Methods


    /**
     * Set a security token for a Catalina context. Can be set only once.
     * 
     * @param name Name of the Catalina context
     * @param token Security token
     */
    public static void setSecurityToken(Object name, Object token) {
        SecurityManager sm = System.getSecurityManager();
        if (sm != null) {
            sm.checkPermission(new RuntimePermission(
                    ContextAccessController.class.getName()
                            + ".setSecurityToken"));
        }
        if ((!securityTokens.containsKey(name)) && (token != null)) {
            securityTokens.put(name, token);
        }
    }


    /**
     * Remove a security token for a context.
     * 
     * @param name Name of the Catalina context
     * @param token Security token
     */
    public static void unsetSecurityToken(Object name, Object token) {
        if (checkSecurityToken(name, token)) {
            securityTokens.remove(name);
        }
    }


    /**
     * Check a submitted security token. The submitted token must be equal to
     * the token present in the repository. If no token is present for the 
     * context, then returns true.
     * 
     * @param name Name of the Catalina context
     * @param token Submitted security token
     */
    public static boolean checkSecurityToken
        (Object name, Object token) {
        Object refToken = securityTokens.get(name);
        return (refToken == null || refToken.equals(token));
    }


    /**
     * Allow writing to a context.
     * 
     * @param name Name of the Catalina context
     * @param token Security token
     */
    public static void setWritable(Object name, Object token) {
        if (checkSecurityToken(name, token))
            readOnlyContexts.remove(name);
    }


    /**
     * Set whether or not a Catalina context is writable.
     * 
     * @param name Name of the Catalina context
     */
    public static void setReadOnly(Object name) {
        readOnlyContexts.put(name, name);
    }


    /**
     * Returns if a context is writable.
     * 
     * @param name Name of the Catalina context
     */
    public static boolean isWritable(Object name) {
        return !(readOnlyContexts.containsKey(name));
    }


}

