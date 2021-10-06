package com.baeldung.jndi.ldap.auth;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Hashtable;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import org.apache.directory.server.annotations.CreateLdapServer;
import org.apache.directory.server.annotations.CreateTransport;
import org.apache.directory.server.core.annotations.ApplyLdifFiles;
import org.apache.directory.server.core.annotations.CreateDS;
import org.apache.directory.server.core.annotations.CreatePartition;
import org.apache.directory.server.core.integ.AbstractLdapTestUnit;
import org.apache.directory.server.core.integ.FrameworkRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(FrameworkRunner.class)
@CreateLdapServer(transports = { @CreateTransport(protocol = "LDAP", address = "localhost", port = 10390)})
@CreateDS(
  allowAnonAccess = false, partitions = {@CreatePartition(name = "TestPartition", suffix = "dc=baeldung,dc=com")})
@ApplyLdifFiles({"users.ldif"})
public class JndiLdapAuthUnitTest extends AbstractLdapTestUnit {

    @Test(expected = Test.None.class /* no exception expected, which shows authentication success */)
    public void givenPreloadedLDAPUserJoe_whenAuthUserWithCorrectPW_thenAuthSucceeds() throws Exception {
        
        Hashtable<String, String> environment = new Hashtable<String, String>();
        environment.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        environment.put(Context.PROVIDER_URL, "ldap://localhost:10390");
        environment.put(Context.SECURITY_AUTHENTICATION, "simple");
        
        environment.put(Context.SECURITY_PRINCIPAL, "cn=Joe Simms,ou=Users,dc=baeldung,dc=com");
        environment.put(Context.SECURITY_CREDENTIALS, "12345");
        
        DirContext context = new InitialDirContext(environment);                
        context.close();
    }
    
    @Test(expected = AuthenticationException.class)
    public void givenPreloadedLDAPUserJoe_whenAuthUserWithWrongPW_thenAuthFails() throws Exception {
        
        Hashtable<String, String> environment = new Hashtable<String, String>();
        environment.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        environment.put(Context.PROVIDER_URL, "ldap://localhost:10390");
        environment.put(Context.SECURITY_AUTHENTICATION, "simple");
        
        environment.put(Context.SECURITY_PRINCIPAL, "cn=Joe Simms,ou=Users,dc=baeldung,dc=com");
        environment.put(Context.SECURITY_CREDENTIALS, "wronguserpw");
        
        DirContext context = new InitialDirContext(environment);   
        context.close();       
    }
    
    @Test(expected = Test.None.class /* no exception expected, which shows authentication success */)
    public void givenPreloadedLDAPUserJoe_whenSearchAndAuthUserWithCorrectPW_thenAuthSucceeds() throws Exception {
        
        // first authenticate against LDAP as admin to search up DN of user : Joe Simms
        
        Hashtable<String, String> environment = new Hashtable<String, String>();
        environment.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        environment.put(Context.PROVIDER_URL, "ldap://localhost:10390");
        environment.put(Context.SECURITY_AUTHENTICATION, "simple");
        
        environment.put(Context.SECURITY_PRINCIPAL, "uid=admin,ou=system");
        environment.put(Context.SECURITY_CREDENTIALS, "secret");
        
        DirContext adminContext = new InitialDirContext(environment);
        
        // define the search filter to find the person with CN : Joe Simms
        String filter = "(&(objectClass=person)(cn=Joe Simms))";

        // declare the attributes we want returned for the object being searched
        String[] attrIDs = { "cn" };

        // define the search controls
        SearchControls searchControls = new SearchControls();
        searchControls.setReturningAttributes(attrIDs);
        searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        
        // search for User with filter cn=Joe Simms
        NamingEnumeration<SearchResult> searchResults = adminContext.search("dc=baeldung,dc=com", filter, searchControls);
        if (searchResults.hasMore()) {
            
            SearchResult result = (SearchResult) searchResults.next();
            Attributes attrs = result.getAttributes();
            
            String distinguishedName = result.getNameInNamespace();
            assertThat("Searched user DN is incorrect: ",
              distinguishedName, is(equalTo("cn=Joe Simms,ou=Users,dc=baeldung,dc=com")));
            
            String commonName = attrs.get("cn").toString();
            assertThat("Searched user CN is incorrect: ", commonName, is(equalTo("cn: Joe Simms")));
            
            // authenticate new context with DN for user Joe Simms, using correct password
        
            environment.put(Context.SECURITY_PRINCIPAL, distinguishedName);
            environment.put(Context.SECURITY_CREDENTIALS, "12345");
            
            DirContext userContext = new InitialDirContext(environment);
            userContext.close();
        }
        
        adminContext.close();               
    }    
    
    @Test(expected = AuthenticationException.class)
    public void givenPreloadedLDAPUserJoe_whenSearchAndAuthUserWithWrongPW_thenAuthFails() throws Exception {
        
        // first authenticate against LDAP as admin to search up DN of user : Joe Simms
        
        Hashtable<String, String> environment = new Hashtable<String, String>();
        environment.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        environment.put(Context.PROVIDER_URL, "ldap://localhost:10390");
        environment.put(Context.SECURITY_AUTHENTICATION, "simple");
        
        environment.put(Context.SECURITY_PRINCIPAL, "uid=admin,ou=system");
        environment.put(Context.SECURITY_CREDENTIALS, "secret");
        
        DirContext adminContext = new InitialDirContext(environment);
        
        // define the search filter to find the person with CN : Joe Simms
        String filter = "(&(objectClass=person)(cn=Joe Simms))";

        // declare the attributes we want returned for the object being searched
        String[] attrIDs = { "cn" };

        // define the search controls
        SearchControls searchControls = new SearchControls();
        searchControls.setReturningAttributes(attrIDs);
        searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        
        // search for User with filter cn=Joe Simms
        NamingEnumeration<SearchResult> searchResults = adminContext.search("dc=baeldung,dc=com", filter, searchControls);
        if (searchResults.hasMore()) {
            
            SearchResult result = (SearchResult) searchResults.next();
            Attributes attrs = result.getAttributes();
            
            String distinguishedName = result.getNameInNamespace();
            assertThat("Searched user DN is incorrect: ",
              distinguishedName, is(equalTo("cn=Joe Simms,ou=Users,dc=baeldung,dc=com")));

            String commonName = attrs.get("cn").toString();
            assertThat("Searched user CN is incorrect: ", commonName, is(equalTo("cn: Joe Simms")));
            
            // authenticate new context with DN for user Joe Simms, using wrong password
        
            environment.put(Context.SECURITY_PRINCIPAL, distinguishedName);
            environment.put(Context.SECURITY_CREDENTIALS, "wronguserpassword");
            
            DirContext userContext = new InitialDirContext(environment);
            userContext.close();
        }
        
        adminContext.close();               
    }
}
