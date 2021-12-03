package com.baeldung.architecture.hexagonal.domain.message.mapper;

import com.baeldung.architecture.hexagonal.common.annotation.Mapper;
import com.baeldung.architecture.hexagonal.domain.message.model.Message;
import com.baeldung.architecture.hexagonal.domain.message.spi.persistence.ISaveMessagePort;

@Mapper
public class MessageMapper {

    public ISaveMessagePort.SaveMessageCommand MessageToSaveMessageCommand(Message message) {
        return new ISaveMessagePort.SaveMessageCommand(message.getBody(), message.getSender(), message.getReceiver());
    }
}
