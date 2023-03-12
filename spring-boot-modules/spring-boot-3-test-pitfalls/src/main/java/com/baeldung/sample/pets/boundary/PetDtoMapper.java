package com.baeldung.sample.pets.boundary;

import com.baeldung.sample.pets.domain.Pet;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PetDtoMapper {

    PetDto map(Pet source);

    Pet map(PetDto source);

}
