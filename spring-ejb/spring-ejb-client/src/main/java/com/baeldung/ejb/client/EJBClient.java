package com.baeldung.ejb.client;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.baeldung.ejb.tutorial.HelloWorld;

public class EJBClient {

    public EJBClient() {
    }

    private Context context = null;

    public String getEJBRemoteMessage() {
        EJBClient main = new EJBClient();
        try {
            // 1. Obtaining Context
            main.createInitialContext();
            // 2. Generate JNDI Lookup name and caste
            HelloWorld helloWorld = main.lookup();
            return helloWorld.getHelloWorld();
        } catch (NamingException e) {
            e.printStackTrace();
            return "";
        } finally {
            try {
                main.closeContext();
            } catch (NamingException e) {
                e.printStackTrace();
            }
        }
    }

    public HelloWorld lookup() throws NamingException {

        // The app name is the EAR name of the deployed EJB without .ear suffix.
        // Since we haven't deployed the application as a .ear, the app name for
        // us will be an empty string
        final String appName = "";
        final String moduleName = "spring-ejb-remote";
        final String distinctName = "";
        final String beanName = "HelloWorld";
        final String viewClassName = HelloWorld.class.getName();
        final String toLookup = String.format("ejb:%s/%s/%s/%s!%s", appName, moduleName, distinctName, beanName, viewClassName);
        return (HelloWorld) context.lookup(toLookup);
    }

    public void createInitialContext() throws NamingException {
        Properties prop = new Properties();
        prop.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
        prop.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
        prop.put(Context.PROVIDER_URL, "http-remoting://127.0.0.1:8080");
        prop.put(Context.SECURITY_PRINCIPAL, "testUser");
        prop.put(Context.SECURITY_CREDENTIALS, "admin1234!");
        prop.put("jboss.naming.client.ejb.context", false);

        context = new InitialContext(prop);
    }

    public void closeContext() throws NamingException {
        if (context != null) {
            context.close();
        }
    }

}
