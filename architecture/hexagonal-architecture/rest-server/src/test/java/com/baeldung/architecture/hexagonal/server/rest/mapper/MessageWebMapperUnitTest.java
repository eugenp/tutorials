package com.baeldung.architecture.hexagonal.server.rest.mapper;

import com.baeldung.architecture.hexagonal.domain.message.model.Message;
import com.baeldung.architecture.hexagonal.server.rest.dto.MessageDTO;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.jeasy.random.FieldPredicates;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

class MessageWebMapperUnitTest {

    private final EasyRandomParameters parameters = new EasyRandomParameters().excludeField(FieldPredicates.named("validator"));
    private final IMessageWebMapper mapper = Mappers.getMapper(IMessageWebMapper.class);
    private final EasyRandom generator = new EasyRandom(parameters);

    @Test
    void givenMessageDTO_whenMap_thenReturnSaveMessage() {

        MessageDTO messageDTO = generator.nextObject(MessageDTO.class);

        Message message = mapper.toModel(messageDTO);

        assertThat(messageDTO.getId()).isEqualTo(message.getId());
        assertThat(messageDTO.getBody()).isEqualTo(message.getBody());
        assertThat(messageDTO.getReceiver()).isEqualTo(message.getReceiver());
        assertThat(messageDTO.getSender()).isEqualTo(message.getSender());
    }

    @Test
    void givenMessage_whenMap_thenReturnSaveMessageDTO() {

        Message message = generator.nextObject(Message.class);

        MessageDTO messageDTO = mapper.toDTO(message);

        assertThat(messageDTO.getId()).isEqualTo(message.getId());
        assertThat(messageDTO.getBody()).isEqualTo(message.getBody());
        assertThat(messageDTO.getReceiver()).isEqualTo(message.getReceiver());
        assertThat(messageDTO.getSender()).isEqualTo(message.getSender());
    }
}

