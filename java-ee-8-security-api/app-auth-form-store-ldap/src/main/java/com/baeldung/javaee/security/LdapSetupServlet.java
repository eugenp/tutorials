package com.baeldung.javaee.security;

import com.unboundid.ldap.listener.InMemoryDirectoryServer;
import com.unboundid.ldap.listener.InMemoryDirectoryServerConfig;
import com.unboundid.ldap.listener.InMemoryListenerConfig;
import com.unboundid.ldap.sdk.LDAPException;
import com.unboundid.ldif.LDIFReader;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet(value = "/init-ldap", loadOnStartup = 1)
public class LdapSetupServlet extends HttpServlet {

    private InMemoryDirectoryServer inMemoryDirectoryServer;

    @Override
    public void init() throws ServletException {
        super.init();
        initLdap();
        System.out.println("@@@START_");
    }

    private void initLdap() {
        try {
            InMemoryDirectoryServerConfig config = new InMemoryDirectoryServerConfig("dc=baeldung,dc=com");
            config.setListenerConfigs(InMemoryListenerConfig.createLDAPConfig("default", 10389));
            config.setSchema(null);
            inMemoryDirectoryServer = new InMemoryDirectoryServer(config);
            inMemoryDirectoryServer.importFromLDIF(true,
                    new LDIFReader(this.getClass().getResourceAsStream("/users.ldif")));
            inMemoryDirectoryServer.startListening();
        } catch (LDAPException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        inMemoryDirectoryServer.shutDown(true);
        System.out.println("@@@END");
    }
}
