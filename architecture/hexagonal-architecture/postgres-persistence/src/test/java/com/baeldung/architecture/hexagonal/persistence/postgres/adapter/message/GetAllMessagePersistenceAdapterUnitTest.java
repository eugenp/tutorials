package com.baeldung.architecture.hexagonal.persistence.postgres.adapter.message;

import com.baeldung.architecture.hexagonal.domain.message.model.Message;
import com.baeldung.architecture.hexagonal.persistence.postgres.adapter.message.mapper.IMessagePersistenceMapper;
import com.baeldung.architecture.hexagonal.persistence.postgres.entity.MessageEntity;
import com.baeldung.architecture.hexagonal.persistence.postgres.repository.IMessageRepository;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.jeasy.random.FieldPredicates;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class GetAllMessagePersistenceAdapterUnitTest {

    private final EasyRandomParameters parameters = new EasyRandomParameters().excludeField(FieldPredicates.named("validator"));
    private final EasyRandom generator = new EasyRandom(parameters);

    private final IMessageRepository messageRepository = mock(IMessageRepository.class);
    private final IMessagePersistenceMapper messagePersistenceMapper = mock(IMessagePersistenceMapper.class);

    private final GetAllMessagePersistenceAdapter getAllMessagePersistenceAdapter = new GetAllMessagePersistenceAdapter(messageRepository);

    @BeforeEach
    void setup() {
        getAllMessagePersistenceAdapter.setMapper(messagePersistenceMapper);
    }

    @Test
    void givenRequest_whenFindAllMessage_theReturnAllMessage() {
        // GIVEN
        int NUMBER_ENTITIES = 3;
        List<MessageEntity> entities = generator.objects(MessageEntity.class, NUMBER_ENTITIES).collect(Collectors.toList());

        List<Message> expectedMessages = generator.objects(Message.class, NUMBER_ENTITIES).collect(Collectors.toList());

        doReturn(entities).when(messageRepository).findAll();
        when(messagePersistenceMapper.entityToModel(any(MessageEntity.class))).thenReturn(expectedMessages.get(0)).thenReturn(expectedMessages.get(1)).thenReturn(expectedMessages.get(2));

        // WHEN
        Optional<Set<Message>> result = getAllMessagePersistenceAdapter.handle();

        // THEN
        assertThat(result).isNotEmpty();

        List<Message> receivedMessages = new ArrayList<>(result.get());
        expectedMessages.sort(Comparator.comparing(Message::getBody));
        receivedMessages.sort(Comparator.comparing(Message::getBody));
        assertEquals(expectedMessages.size(), receivedMessages.size());

        for (int i = 0; i < receivedMessages.size(); i++)
            assertThat(expectedMessages.get(i)).usingRecursiveComparison().isEqualTo(receivedMessages.get(i));

        verify(messageRepository).findAll();
        verify(messagePersistenceMapper, times(NUMBER_ENTITIES)).entityToModel(any(MessageEntity.class));
    }

}
