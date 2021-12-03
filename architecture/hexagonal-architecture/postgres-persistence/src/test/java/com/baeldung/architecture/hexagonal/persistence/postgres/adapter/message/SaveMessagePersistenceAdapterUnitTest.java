package com.baeldung.architecture.hexagonal.persistence.postgres.adapter.message;

import com.baeldung.architecture.hexagonal.domain.message.model.Message;
import com.baeldung.architecture.hexagonal.domain.message.spi.persistence.ISaveMessagePort.SaveMessageCommand;
import com.baeldung.architecture.hexagonal.persistence.postgres.adapter.message.mapper.IMessagePersistenceMapper;
import com.baeldung.architecture.hexagonal.persistence.postgres.entity.MessageEntity;
import com.baeldung.architecture.hexagonal.persistence.postgres.repository.IMessageRepository;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.jeasy.random.FieldPredicates;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

class SaveMessagePersistenceAdapterUnitTest {

    private final EasyRandomParameters parameters = new EasyRandomParameters().excludeField(FieldPredicates.named("validator"));
    private final EasyRandom generator = new EasyRandom(parameters);

    private final IMessageRepository repository = mock(IMessageRepository.class);
    private final IMessagePersistenceMapper mapper = mock(IMessagePersistenceMapper.class);

    private final SaveMessagePersistenceAdapter adapter = new SaveMessagePersistenceAdapter(repository);

    @BeforeEach
    void setup() {
        adapter.setMapper(mapper);
    }

    @Test
    void whenSaveMessage_thenFailAndReturnEmpty() {
        // GIVEN
        SaveMessageCommand command = generator.nextObject(SaveMessageCommand.class);
        MessageEntity entity = generator.nextObject(MessageEntity.class);

        doReturn(entity).when(mapper).saveMessageCommandToEntity(command);
        doThrow(new IllegalArgumentException()).when(repository).save(entity);

        // WHEN
        Optional<Message> result = adapter.handle(command);

        // THEN
        assertThat(result).isEmpty();
        then(mapper).should().saveMessageCommandToEntity(command);
        then(repository).should().save(entity);
    }

    @Test
    void whenSaveMessage_thenSucceedAndReturnMessage() {
        // GIVEN
        SaveMessageCommand command = generator.nextObject(SaveMessageCommand.class);
        MessageEntity entity = generator.nextObject(MessageEntity.class);
        MessageEntity entitySaved = generator.nextObject(MessageEntity.class);
        Message message = generator.nextObject(Message.class);

        doReturn(entity).when(mapper).saveMessageCommandToEntity(command);
        doReturn(entitySaved).when(repository).save(entity);
        doReturn(message).when(mapper).entityToModel(entitySaved);

        // WHEN
        Optional<Message> result = adapter.handle(command);

        // THEN
        assertThat(result).contains(message);
        then(mapper).should().saveMessageCommandToEntity(command);
        then(repository).should().save(entity);
        then(mapper).should().entityToModel(entitySaved);
    }

}
