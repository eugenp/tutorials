package com.baeldung.architecture.hexagonal.server.rest.mapper;

import com.baeldung.architecture.hexagonal.common.annotation.Mapper;
import com.baeldung.architecture.hexagonal.domain.message.model.Message;
import com.baeldung.architecture.hexagonal.server.rest.dto.MessageDTO;

@Mapper
public interface MessageDTOMapper {
    Message toModel(MessageDTO dto);
}
