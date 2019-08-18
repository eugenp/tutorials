package com.baeldung.mapper;

import com.baeldung.dto.UrlDTO;
import com.baeldung.entity.UrlObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UrlMapperTest {

    @Test
    public void givenDomains_whenCallDomainMapper_thenReturnDomainsWithoutProtocols() {
        UrlDTO dto = new UrlDTO();
        dto.setUrl("http://www.baeldung.com");
        dto.setSubUrl("https://www.baeldung.com");

        UrlObject urlObject = UrlMapper.INSTANCE.urlObjectDomainMapper(dto);

        assertEquals(urlObject.getUrl(), "www.baeldung.com");
        assertEquals(urlObject.getSubUrl(), "www.baeldung.com");
    }
}