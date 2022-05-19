package com.baeldung.networking.domain_name;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DomainNameClientUnitTest {

    DomainNameClient domainNameClient = new DomainNameClient();

    @Test
    void givenUrl_whenGetHost_thenReturnSubdomainAndDomainName() {
        Assertions.assertAll(() -> {
            assertEquals("www.baeldung.com", domainNameClient.getHost("https://www.baeldung.com/domain"));
            assertEquals("www.google.co.uk", domainNameClient.getHost("https://www.google.co.uk/domain"));
            assertEquals("jira.baeldung.com", domainNameClient.getHost("https://jira.baeldung.com/secure"));
        });
    }

    @Test
    void givenUrl_whenGetTopPrivateDomain_thenReturnDomainName() {
        assertEquals("baeldung.com", domainNameClient.getTopPrivateDomain("www.baeldung.com"));
    }

    @Test
    void givenUrlWithPublicSuffix_whenGetTopPrivateDomain_thenReturnDomainName() {
        assertEquals("google.co.uk", domainNameClient.getTopPrivateDomain("www.google.co.uk"));
        assertEquals("baeldung.blogspot.com", domainNameClient.getTopPrivateDomain("www.baeldung.blogspot.com"));
    }

    @Test
    void givenUrlWithPublicSuffix_whenGetName_thenReturnSecondLevelDomain() {
        assertEquals("baeldung", domainNameClient.getName("jira.baeldung.com"));
    }

    @Test
    void givenUrlWithPublicSuffix_whenGetName_thenReturnThirdLevelDomain() {
        assertEquals("baeldung", domainNameClient.getName("www.baeldung.co.uk"));
        assertEquals("google", domainNameClient.getName("www.google.co.uk"));
    }

    @Test
    void givenUrl_whenGetDomainNameRegex_thenReturnDomainName() {
        assertEquals("google.com", domainNameClient.getDomainName("www.google.com"));
        assertEquals("google.co.uk", domainNameClient.getDomainName("www.google.co.uk"));
        assertEquals("jira.baeldung.com", domainNameClient.getDomainName("jira.baeldung.com"));
        assertEquals("baeldung.com", domainNameClient.getDomainName("www.baeldung.com/test"));
    }

}
