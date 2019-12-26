package com.baeldung.ejb.wildfly;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;

public class TextApplication {

    public static void main(String[] args) throws NamingException {
        TextProcessorRemote textProcessor = EJBFactory.createTextProcessorBeanFromJNDI("ejb:");
        System.out.print(textProcessor.processText("sample text"));
    }

    private static class EJBFactory {

        private static TextProcessorRemote createTextProcessorBeanFromJNDI(String namespace) throws NamingException {
            return lookupTextProcessorBean(namespace);
        }

        private static TextProcessorRemote lookupTextProcessorBean(String namespace) throws NamingException {
            Context ctx = createInitialContext();
            final String appName = "";
            final String moduleName = "spring-ejb-remote";
            final String distinctName = "";
            final String beanName = TextProcessorBean.class.getSimpleName();
            final String viewClassName = TextProcessorRemote.class.getName();
            return (TextProcessorRemote) ctx.lookup(namespace + appName + "/" + moduleName + "/" + distinctName + "/" + beanName + "!" + viewClassName);
        }

        private static Context createInitialContext() throws NamingException {
            Properties jndiProperties = new Properties();
            jndiProperties.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
            jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
            jndiProperties.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");
            jndiProperties.put("jboss.naming.client.ejb.context", true);
            return new InitialContext(jndiProperties);
        }
    }
}
