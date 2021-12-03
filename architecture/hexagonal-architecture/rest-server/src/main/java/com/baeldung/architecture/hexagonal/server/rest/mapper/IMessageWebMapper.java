package com.baeldung.architecture.hexagonal.server.rest.mapper;

import com.baeldung.architecture.hexagonal.domain.message.model.Message;
import com.baeldung.architecture.hexagonal.server.rest.dto.MessageDTO;
import org.mapstruct.Mapper;

@Mapper
public interface IMessageWebMapper {

    Message toModel(MessageDTO dto);

    MessageDTO toDTO(Message model);
}
