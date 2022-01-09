package com.baeldung.jndi.ldap.connectionTool;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

public class LdapConnectionTool {
    private static boolean DEBUG_MODE = Boolean.valueOf(System.getProperty("debug.mode", "false"));

    public static void main(String[] args) throws NamingException {
        String factory = System.getProperty("factory", "com.sun.jndi.ldap.LdapCtxFactory");
        String authType = System.getProperty("authType", "none");
        String url = System.getProperty("url");
        String user = System.getProperty("user");
        String password = System.getProperty("password");
        String query = System.getProperty("query", user);

        if (url == null) {
            throw new IllegalArgumentException("please provide 'url' system property");
        }

        Hashtable<String, String> env = new Hashtable<String, String>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, factory);
        env.put("com.sun.jndi.ldap.read.timeout", "5000");
        env.put("com.sun.jndi.ldap.connect.timeout", "5000");
        env.put(Context.SECURITY_AUTHENTICATION, authType);
        env.put(Context.PROVIDER_URL, url);
        if (user != null) {
            if (password == null) {
                throw new IllegalArgumentException("please provide 'password' system property");
            }
            env.put(Context.SECURITY_PRINCIPAL, user);
            env.put(Context.SECURITY_CREDENTIALS, password);
        }

        DirContext context = null;
        try {
            System.out.println("connecting to " + url);
            context = new InitialDirContext(env);
            System.out.println("successfully connected to " + url);

            if (query != null) {
                Attributes attributes = context.getAttributes(query);
                NamingEnumeration<? extends Attribute> all = attributes.getAll();
                while (all.hasMoreElements()) {
                    Attribute next = all.next();

                    String key = next.getID();
                    Object value = next.get();

                    System.out.println(key + "=" + value);
                }
            }
        } catch (NamingException e) {
            System.out.println(e.getClass() + ": " + e.getMessage());
            Throwable cause = e.getRootCause();
            if (cause != null) {
                System.out.println(cause.getClass() + ": " + cause.getMessage());
            }

            if (DEBUG_MODE)
                throw e;
        } finally {
            if (context != null) {
                try {
                    context.close();
                } catch (NamingException e) {
                    System.out.println(e.getMessage());

                    if (DEBUG_MODE)
                        throw e;
                }
            }
        }
    }
}
