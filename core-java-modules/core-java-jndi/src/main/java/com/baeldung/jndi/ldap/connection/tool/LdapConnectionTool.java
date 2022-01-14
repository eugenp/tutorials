package com.baeldung.jndi.ldap.connection.tool;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

public class LdapConnectionTool {
    private static final boolean DEBUG_MODE = Boolean.parseBoolean(System.getProperty("debug.mode", "false"));
    private static final String QUERY = "query";

    public static void main(String[] args) throws NamingException {
        execute();
    }

    public static void execute() throws NamingException {
        Hashtable<String, String> env = createEnvironmentFromProperties();

        DirContext context = null;
        try {
            context = connectToServer(env);

            String query = env.get(LdapConnectionTool.QUERY);
            if (query != null) {
                executeQuery(context, query);
            }
        } catch (NamingException e) {
            showErrorMessage(e);
        } finally {
            close(context);
        }
    }

    private static void close(DirContext context) throws NamingException {
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

    private static void showErrorMessage(NamingException e) throws NamingException {
        System.out.println(e.getClass() + ": " + e.getMessage());
        Throwable cause = e.getRootCause();
        if (cause != null) {
            System.out.println(cause.getClass() + ": " + cause.getMessage());
        }

        if (DEBUG_MODE)
            throw e;
    }

    private static DirContext connectToServer(Hashtable<String, String> env) throws NamingException {
        String url = env.get(Context.PROVIDER_URL);

        System.out.println("connecting to " + url + "...");
        DirContext context = new InitialDirContext(env);
        System.out.println("successfully connected to " + url);
        return context;
    }

    private static void executeQuery(DirContext context, String query) throws NamingException {
        Attributes attributes = context.getAttributes(query);
        NamingEnumeration<? extends Attribute> all = attributes.getAll();
        while (all.hasMoreElements()) {
            Attribute next = all.next();

            String key = next.getID();
            Object value = next.get();

            System.out.println(key + "=" + value);
        }
    }

    private static Hashtable<String, String> createEnvironmentFromProperties() {
        String factory = System.getProperty("factory", "com.sun.jndi.ldap.LdapCtxFactory");
        String authType = System.getProperty("authType", "none");
        String url = System.getProperty("url");
        String user = System.getProperty("user");
        String password = System.getProperty("password");
        String query = System.getProperty(QUERY, user);

        if (url == null) {
            throw new IllegalArgumentException("please provide 'url' system property");
        }

        Hashtable<String, String> env = new Hashtable<>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, factory);
        env.put("com.sun.jndi.ldap.read.timeout", "5000");
        env.put("com.sun.jndi.ldap.connect.timeout", "5000");
        env.put(Context.SECURITY_AUTHENTICATION, authType);
        env.put(Context.PROVIDER_URL, url);
        if (query != null) {
            env.put(LdapConnectionTool.QUERY, query);
        }

        if (user != null) {
            if (password == null) {
                throw new IllegalArgumentException("please provide 'password' system property");
            }
            env.put(Context.SECURITY_PRINCIPAL, user);
            env.put(Context.SECURITY_CREDENTIALS, password);
        }
        return env;
    }
}
