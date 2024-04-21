package com.baeldung.inheritance.mapper;

import org.mapstruct.Mapper;

import com.baeldung.inheritance.model.Bus;
import com.baeldung.inheritance.model.dto.BusDTO;

@Mapper()
public interface BusMapper {

    BusDTO busToDTO(Bus bus);
}
