package com.baeldung.mapper;

import com.baeldung.dto.UrlDTO;
import com.baeldung.entity.UrlObject;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.net.URI;
import java.net.URISyntaxException;

@Mapper
public interface UrlMapper {

    UrlMapper INSTANCE = Mappers.getMapper(UrlMapper.class);


    @Mapping(source = "subUrl", target = "subUrl", qualifiedByName = "protocolRemover")
    @Mapping(source = "url", target = "url", qualifiedBy = SuffixRemover.class)
    public UrlObject urlObjectDomainMapper(UrlDTO urlDTO);

    @Named("protocolRemover")
    public static String protocolRemoverWithCustomMethod(String domain) throws URISyntaxException {
        URI uri = new URI(domain);
        return uri.getHost();
    }

    @SuffixRemover
    public static String protocolRemoverMethod(String domain) throws URISyntaxException {
        URI uri = new URI(domain);
        return uri.getHost();
    }
}