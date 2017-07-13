package com.baeldung.application;

import com.baeldung.ejb.stateless.TextProcessorBean;
import com.baeldung.ejb.stateless.TextProcessorRemote;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;

public class TextApplication {

    public static void main(String[] args) throws NamingException {
        Context context = createInitialContext();
        TextProcessorRemote textProcessor = lookupTextProcessorBean(context, "ejb:");
        System.out.println(textProcessor.processText("sample text"));
    }

    private static TextProcessorRemote lookupTextProcessorBean(Context ctx, String namespace) throws NamingException {
        final String appName = "";
        final String moduleName = "EJBModule";
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
