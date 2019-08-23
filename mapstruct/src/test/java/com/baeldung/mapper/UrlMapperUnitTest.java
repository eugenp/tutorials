package com.baeldung.mapper;

import com.baeldung.dto.UrlDTO;
import com.baeldung.entity.UrlObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UrlMapperUnitTest {

    @Test
    public void givenDomain_whenCallNamedMapper_thenReturnDomainWithoutProtocol() {
        UrlDTO dto = new UrlDTO();
        dto.setAddress("http://www.baeldung.com/mapstruct");

        UrlObject urlObject = UrlMapper.INSTANCE.urlObjectNamedMapper(dto);

        assertEquals(urlObject.getAddress(), "www.baeldung.com/mapstruct");
    }

    @Test
    public void givenAddress_whenCallAnnotatedMapper_thenReturnDomainWithoutProtocol() {
        UrlDTO dto = new UrlDTO();
        dto.setAddress("http://www.baeldung.com/customMappers");

        UrlObject urlObject = UrlMapper.INSTANCE.urlObjectAnnotatedMapper(dto);

        assertEquals(urlObject.getAddress(), "www.baeldung.com/customMappers");
    }
}