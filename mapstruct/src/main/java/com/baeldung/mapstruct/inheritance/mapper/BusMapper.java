package com.baeldung.mapstruct.inheritance.mapper;

import org.mapstruct.Mapper;

import com.baeldung.mapstruct.inheritance.model.Bus;
import com.baeldung.mapstruct.inheritance.model.dto.BusDTO;

@Mapper()
public interface BusMapper {

    BusDTO busToDTO(Bus bus);
}
