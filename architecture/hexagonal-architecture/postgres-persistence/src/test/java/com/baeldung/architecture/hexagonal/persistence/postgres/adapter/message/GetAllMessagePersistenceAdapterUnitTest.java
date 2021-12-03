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

    private final IMessageRepository repository = mock(IMessageRepository.class);
    private final IMessagePersistenceMapper mapper = mock(IMessagePersistenceMapper.class);

    private final GetAllMessagePersistenceAdapter adapter = new GetAllMessagePersistenceAdapter(repository);

    @BeforeEach
    void setup() {
        adapter.setMapper(mapper);
    }

    @Test
    void givenRequest_whenFindAllMessage_theReturnAllMessage() {
        // GIVEN
        int NUMBER_ENTITIES = 3;
        List<MessageEntity> entities = generator.objects(MessageEntity.class, NUMBER_ENTITIES).collect(Collectors.toList());

        List<Message> Messages = generator.objects(Message.class, NUMBER_ENTITIES).collect(Collectors.toList());

        doReturn(entities).when(repository).findAll();
        when(mapper.entityToModel(any(MessageEntity.class))).thenReturn(Messages.get(0)).thenReturn(Messages.get(1)).thenReturn(Messages.get(2));

        // WHEN
        Optional<Set<Message>> result = adapter.handle();

        // THEN
        List<Message> resultList = new ArrayList<>(result.get());
        Messages.sort(Comparator.comparing(Message::getBody));
        resultList.sort(Comparator.comparing(Message::getBody));
        assertEquals(Messages.size(), resultList.size());

        for (int i = 0; i < resultList.size(); i++)
            assertThat(Messages.get(i)).isEqualToIgnoringGivenFields(resultList.get(i));

        verify(repository).findAll();
        verify(mapper, times(NUMBER_ENTITIES)).entityToModel(any(MessageEntity.class));
    }

}
