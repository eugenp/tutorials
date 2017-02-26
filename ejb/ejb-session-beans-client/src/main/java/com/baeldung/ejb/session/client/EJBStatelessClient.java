package com.baeldung.ejb.session.client;

import java.util.List;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.baeldung.ejb.stateless.beans.ItemStatelessRemote;

public class EJBStatelessClient {

    public EJBStatelessClient() {
    }

    private Context context = null;

    public static void main(String[] arg) {
        EJBStatelessClient ejb = new EJBStatelessClient();
        System.out.println(ejb.getEJBRemoteMessage());

    }

    public Boolean getEJBRemoteMessage() {
        EJBStatelessClient main = new EJBStatelessClient();
        Boolean result = true;
        try {
            // 1. Obtaining Context
            main.createInitialContext();
            // 2. Generate JNDI Lookup name and caste
            ItemStatelessRemote itemStatelessOne = main.lookup();
            ItemStatelessRemote itemStatelessTwo = main.lookup();

            itemStatelessOne.addItem("Book");
            itemStatelessOne.addItem("Pen");
            itemStatelessOne.addItem("Pencil");
            itemStatelessOne.addItem("Eraser");

            result = itemStatelessOne.getItemList().equals(itemStatelessTwo.getItemList());

            return result;
        } catch (NamingException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                main.closeContext();
            } catch (NamingException e) {
                e.printStackTrace();
            }
        }
    }

    public ItemStatelessRemote lookup() throws NamingException {

        // The app name is the EAR name of the deployed EJB without .ear suffix.
        // Since we haven't deployed the application as a .ear, the app name for
        // us will be an empty string
        final String appName = "";
        final String moduleName = "session-beans";
        final String distinctName = "";
        final String beanName = "ItemStatelessRemote";
        final String viewClassName = ItemStatelessRemote.class.getName();
        final String toLookup = String.format("ejb:%s/%s/%s/%s!%s", appName, moduleName, distinctName, beanName, viewClassName);

        return (ItemStatelessRemote) context.lookup(toLookup);
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
