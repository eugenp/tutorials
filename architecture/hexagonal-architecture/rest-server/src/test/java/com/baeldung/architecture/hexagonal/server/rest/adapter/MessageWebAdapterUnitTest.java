package com.baeldung.architecture.hexagonal.server.rest.adapter;

import com.baeldung.architecture.hexagonal.domain.message.api.persistence.IGetAllMessageService;
import com.baeldung.architecture.hexagonal.domain.message.api.persistence.ISaveMessageService;
import com.baeldung.architecture.hexagonal.domain.message.model.Message;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

class MessageWebAdapterUnitTest {

    private final EasyRandom generator = new EasyRandom();
    private final IGetAllMessageService getAllMessageService = mock(IGetAllMessageService.class);
    private final ISaveMessageService saveMessageService = mock(ISaveMessageService.class);
    private final MessageWebAdapter adapter = new MessageWebAdapter(getAllMessageService, saveMessageService);

    @Test
    void givenRequest_whenFindAllMessage_thenReturnAllMessage() {
        // GIVEN
        int NUMBER_ENTITIES = 3;
        Set<Message> Messages = generator.objects(Message.class, NUMBER_ENTITIES).collect(Collectors.toSet());
        doReturn(of(Messages)).when(getAllMessageService).handle();

        // WHEN
        List<Message> result = adapter.getAll();

        // THEN
        assertThat(Messages).containsAll(result);
    }

    @Test
    void givenRequest_whenFindAllMessage_thenFailAndReturnEmptyMessageList() {
        // GIVEN
        doReturn(empty()).when(getAllMessageService).handle();

        // WHEN
        List<Message> result = adapter.getAll();

        // THEN
        assertThat(result).isEmpty();
    }

    @Test
    void givenRequest_whenCreateMessage_thenReturnCreatedMessage() {
        // GIVEN
        Message dto = generator.nextObject(Message.class);
        Message expectedMessage = generator.nextObject(Message.class);
        doReturn(of(expectedMessage)).when(saveMessageService).handle(dto);

        // WHEN
        Message result = adapter.create(dto);

        // THEN
        assertThat(expectedMessage).isEqualTo(result);
    }

    @Test
    void givenRequest_whenCreateMessage_thenReturnNull() {
        // GIVEN
        Message dto = generator.nextObject(Message.class);
        doReturn(empty()).when(saveMessageService).handle(dto);

        // WHEN
        Message result = adapter.create(dto);

        // THEN
        assertThat(result).isNull();
    }
}
