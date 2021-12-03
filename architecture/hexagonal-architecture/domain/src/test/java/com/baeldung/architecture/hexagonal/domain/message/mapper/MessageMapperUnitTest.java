package com.baeldung.architecture.hexagonal.domain.message.mapper;

import com.baeldung.architecture.hexagonal.domain.message.model.Message;
import com.baeldung.architecture.hexagonal.domain.message.spi.persistence.ISaveMessagePort.SaveMessageCommand;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MessageMapperUnitTest {

    private static final MessageMapper mapper = new MessageMapper();

    private final EasyRandom generator = new EasyRandom();

    @Test
    void givenMessage_whenMap_thenReturnSaveMessageCommand() {
        // GIVEN
        Message message = generator.nextObject(Message.class);

        // WHEN
        SaveMessageCommand command = mapper.MessageToSaveMessageCommand(message);

        // THEN
        assertThat(message.getBody()).isEqualTo(command.getBody());
        assertThat(message.getSender()).isEqualTo(command.getSender());
        assertThat(message.getReceiver()).isEqualTo(command.getReceiver());
    }
}
