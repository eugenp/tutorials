package com.baeldung.mapper;

import com.baeldung.dto.UrlDTO;
import com.baeldung.entity.UrlObject;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

@Mapper
public interface UrlMapper {

    UrlMapper INSTANCE = Mappers.getMapper(UrlMapper.class);


    @Mapping(source = "address", target = "address", qualifiedByName = "protocolRemover")
    public UrlObject urlObjectNamedMapper(UrlDTO urlDTO);

    @Mapping(source = "address", target = "address", qualifiedBy = ProtocolRemover.class)
    public UrlObject urlObjectAnnotatedMapper(UrlDTO urlDTO);

    @Named("protocolRemover")
    public static String protocolRemoverWithCustomMethod(String address) throws URISyntaxException {
        URI uri = new URI(address);

        return uri.getHost() + uri.getPath();
    }

    @ProtocolRemover
    public static String protocolRemoverMethod(String address) throws URISyntaxException {
        URI uri = new URI(address);

        return uri.getHost() + uri.getPath();
    }
}