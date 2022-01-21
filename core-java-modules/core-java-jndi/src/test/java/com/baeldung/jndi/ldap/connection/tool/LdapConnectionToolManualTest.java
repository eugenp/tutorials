package com.baeldung.jndi.ldap.connection.tool;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import javax.naming.NamingException;

import org.apache.directory.server.annotations.CreateLdapServer;
import org.apache.directory.server.annotations.CreateTransport;
import org.apache.directory.server.core.annotations.ApplyLdifFiles;
import org.apache.directory.server.core.annotations.CreateDS;
import org.apache.directory.server.core.annotations.CreatePartition;
import org.apache.directory.server.core.integ.AbstractLdapTestUnit;
import org.apache.directory.server.core.integ.FrameworkRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(FrameworkRunner.class)
@CreateLdapServer(allowAnonymousAccess = true, transports = { @CreateTransport(protocol = "LDAP", address = "localhost", port = 10389) })
@CreateDS(allowAnonAccess = true, partitions = { @CreatePartition(name = "ldap-connection-tool", suffix = "dc=baeldung,dc=com") })
@ApplyLdifFiles({ "ldap-connection-tool.ldif" })
// class marked as manual test, as it has to run independently of other unit tests in the module
public class LdapConnectionToolManualTest extends AbstractLdapTestUnit {
    @Before
    public void init() {
        System.setProperty("debug.mode", "true");
        System.clearProperty("url");
        System.clearProperty("user");
        System.clearProperty("password");
        System.clearProperty("query");
    }

    @Test
    public void whenNoUrlProvided_thenConnectionFails() throws Exception {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> LdapConnectionTool.execute());
    }

    @Test
    public void givenUrlProvided_whenValidUrl_thenConnectionSucceeds() throws Exception {
        System.setProperty("url", "ldap://localhost:10389");

        assertThatCode(() -> LdapConnectionTool.execute()).doesNotThrowAnyException();
    }

    @Test
    public void givenUrlProvided_whenInvalidUrl_thenConnectionFails() throws Exception {
        System.setProperty("url", "ldap://unkownhost:10389");

        assertThatExceptionOfType(NamingException.class).isThrownBy(() -> LdapConnectionTool.execute());
    }

    @Test
    public void givenUserProvided_whenCorrectPassword_thenConnectionSucceeds() throws Exception {
        System.setProperty("url", "ldap://localhost:10389");
        System.setProperty("user", "uid=gauss,dc=baeldung,dc=com");
        System.setProperty("password", "password");

        assertThatCode(() -> LdapConnectionTool.execute()).doesNotThrowAnyException();
    }

    @Test
    public void givenUserProvided_whenPasswordIsNull_thenConnectionFails() throws Exception {
        System.setProperty("url", "ldap://localhost:10389");
        System.setProperty("user", "uid=gauss,dc=baeldung,dc=com");

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> LdapConnectionTool.execute());
    }

    @Test
    public void whenOnlyValidQueryProvided_thenConnectionSucceeds() throws Exception {
        System.setProperty("url", "ldap://localhost:10389");
        System.setProperty("query", "uid=gauss,dc=baeldung,dc=com");

        assertThatCode(() -> LdapConnectionTool.execute()).doesNotThrowAnyException();
    }

    @Test
    public void givenUserProvided_whenQueryProvided_thenConnectionSucceeds() throws Exception {
        System.setProperty("url", "ldap://localhost:10389");
        System.setProperty("user", "uid=gauss,dc=baeldung,dc=com");
        System.setProperty("password", "password");
        System.setProperty("query", "uid=newton,dc=baeldung,dc=com");

        assertThatCode(() -> LdapConnectionTool.execute()).doesNotThrowAnyException();
    }
}
