package com.baeldung.architecture.hexagonal.persistence.postgres.adapter.message.mapper;

import com.baeldung.architecture.hexagonal.domain.message.model.Message;
import com.baeldung.architecture.hexagonal.domain.message.spi.persistence.ISaveMessagePort.SaveMessageCommand;
import com.baeldung.architecture.hexagonal.persistence.postgres.adapter.parent.mapper.IGenericMapper;
import com.baeldung.architecture.hexagonal.persistence.postgres.entity.MessageEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface IMessagePersistenceMapper extends IGenericMapper<Message, MessageEntity> {

    @Mapping(target = "id", ignore = true)
    MessageEntity saveMessageCommandToEntity(SaveMessageCommand command);

}
