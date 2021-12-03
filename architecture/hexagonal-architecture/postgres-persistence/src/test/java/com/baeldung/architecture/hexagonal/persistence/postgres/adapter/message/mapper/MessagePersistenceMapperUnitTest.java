package com.baeldung.architecture.hexagonal.persistence.postgres.adapter.message.mapper;

import com.baeldung.architecture.hexagonal.domain.message.model.Message;
import com.baeldung.architecture.hexagonal.domain.message.spi.persistence.ISaveMessagePort.SaveMessageCommand;
import com.baeldung.architecture.hexagonal.persistence.postgres.entity.MessageEntity;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.jeasy.random.FieldPredicates;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

class MessagePersistenceMapperUnitTest {

    private final EasyRandomParameters parameters = new EasyRandomParameters().excludeField(FieldPredicates.named("validator"));
    private final EasyRandom generator = new EasyRandom(parameters);

    private final IMessagePersistenceMapper mapper = Mappers.getMapper(IMessagePersistenceMapper.class);

    @Test
    void givenEntity_whenMap_thenReturnModel() {
        // GIVEN
        MessageEntity entity = generator.nextObject(MessageEntity.class);

        // WHEN
        Message model = mapper.entityToModel(entity);

        // THEN
        assertThat(entity.getId()).isEqualTo(model.getId());
        assertThat(entity.getBody()).isEqualTo(model.getBody());
        assertThat(entity.getReceiver()).isEqualTo(model.getReceiver());
        assertThat(entity.getSender()).isEqualTo(model.getSender());
    }

    @Test
    void givenSaveMessageCommand_whenMap_thenReturnEntity() {
        // GIVEN
        SaveMessageCommand command = generator.nextObject(SaveMessageCommand.class);

        // WHEN
        MessageEntity entity = mapper.saveMessageCommandToEntity(command);

        // THEN
        assertThat(command.getBody()).isEqualTo(entity.getBody());
        assertThat(command.getReceiver()).isEqualTo(entity.getReceiver());
        assertThat(command.getSender()).isEqualTo(entity.getSender());
    }
}
