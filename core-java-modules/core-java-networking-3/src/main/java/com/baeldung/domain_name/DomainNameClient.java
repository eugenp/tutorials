package com.baeldung.domain_name;

import com.google.common.net.InternetDomainName;

import java.net.URI;
import java.net.URISyntaxException;

public class DomainNameClient {

    public String getHost(String url) throws URISyntaxException {
        URI uri = new URI(url);
        return uri.getHost();
    }

    public String getTopPrivateDomain(String url) {
        InternetDomainName internetDomainName = InternetDomainName.from(url)
            .topPrivateDomain();
        return internetDomainName.toString();
    }

    public String getName(String url) {
        InternetDomainName internetDomainName = InternetDomainName.from(url)
            .topPrivateDomain();
        String publicSuffix = internetDomainName.publicSuffix()
            .toString();
        String domainName = internetDomainName.toString();
        return domainName.substring(0, domainName.lastIndexOf("." + publicSuffix));
    }

    public String getDomainName(String url) {
        String regex = "http(s)?://|www\\.|/.*";
        return url.replaceAll(regex, "");
    }
}
