package com.baeldung.mapper;

import com.baeldung.dto.AncestorDto;
import com.baeldung.entity.GrandDad;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface AncestorMapper {

    @Mapping(source = "identity.name", target = "firstName")
    @Mapping(source = "weight", target = "weight", numberFormat = "###.#")
    AncestorDto toDto(GrandDad grandDad);

}
