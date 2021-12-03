package com.baeldung.architecture.hexagonal.server.rest.adapter;

import com.baeldung.architecture.hexagonal.common.utils.CollectionUtils;
import com.baeldung.architecture.hexagonal.domain.message.api.persistence.IGetAllMessageService;
import com.baeldung.architecture.hexagonal.domain.message.api.persistence.ISaveMessageService;
import com.baeldung.architecture.hexagonal.domain.message.model.Message;
import com.baeldung.architecture.hexagonal.server.rest.dto.MessageDTO;
import com.baeldung.architecture.hexagonal.server.rest.mapper.IMessageWebMapper;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.jeasy.random.FieldPredicates;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

class MessageWebAdapterUnitTest {

    private final EasyRandomParameters parameters = new EasyRandomParameters().excludeField(FieldPredicates.named("validator"));
    private final EasyRandom generator = new EasyRandom(parameters);

    private final IGetAllMessageService getAllMessageService = mock(IGetAllMessageService.class);
    private final ISaveMessageService saveMessageService = mock(ISaveMessageService.class);
    private final MessageWebAdapter adapter = new MessageWebAdapter(getAllMessageService, saveMessageService);

    private final IMessageWebMapper mapper = mock(IMessageWebMapper.class);

    @BeforeEach
    void setup() {
        adapter.setMapper(mapper);
    }

    @Test
    void givenRequest_whenFindAllMessage_thenReturnAllMessage() {
        // GIVEN
        int NUMBER_MODELS = 3;
        Set<Message> expectedMessages = generator.objects(Message.class, NUMBER_MODELS).collect(Collectors.toSet());
        Set<MessageDTO> expectedMessageDTOs = generator.objects(MessageDTO.class, NUMBER_MODELS).collect(Collectors.toSet());

        doReturn(of(expectedMessages)).when(getAllMessageService).handle();

        CollectionUtils.zip(new ArrayList<>(expectedMessageDTOs), new ArrayList<>(expectedMessages)).forEach(tuple -> {

            MessageDTO dto = (MessageDTO) tuple.get(0);
            Message model = (Message) tuple.get(1);

            doReturn(dto).when(mapper).toDTO(model);
        });

        // WHEN
        List<MessageDTO> result = adapter.getAll();

        CollectionUtils.zip(new ArrayList<>(expectedMessageDTOs), result).forEach(tuple -> {

            MessageDTO expectedDTO = tuple.get(0);
            MessageDTO receivedDTO = tuple.get(1);

            assertThat(expectedDTO).isEqualTo(receivedDTO);
        });

        // THEN
        assertThat(expectedMessageDTOs).containsAll(result);
    }

    @Test
    void givenRequest_whenFindAllMessage_thenFailAndReturnEmptyMessageList() {
        // GIVEN
        Set<Message> emptyMessages = new HashSet<>();
        doReturn(Optional.of(emptyMessages)).when(getAllMessageService).handle();

        // WHEN
        List<MessageDTO> result = adapter.getAll();

        // THEN
        assertThat(result).isEmpty();
    }

    @Test
    void givenRequest_whenCreateMessage_thenReturnCreatedMessage() {
        // GIVEN
        MessageDTO receivedMessageDTO = generator.nextObject(MessageDTO.class);
        Message receivedMessage = generator.nextObject(Message.class);
        MessageDTO expectedMessageDTO = generator.nextObject(MessageDTO.class);
        Message expectedMessage = generator.nextObject(Message.class);

        doReturn(receivedMessage).when(mapper).toModel(receivedMessageDTO);
        doReturn(of(expectedMessage)).when(saveMessageService).handle(receivedMessage);
        doReturn(expectedMessageDTO).when(mapper).toDTO(expectedMessage);

        // WHEN
        MessageDTO result = adapter.create(receivedMessageDTO);

        // THEN
        assertThat(expectedMessageDTO).isEqualTo(result);
    }

    @Test
    void givenRequest_whenCreateMessage_thenReturnNull() {
        // GIVEN
        MessageDTO receivedMessageDTO = generator.nextObject(MessageDTO.class);
        Message receivedMessage = generator.nextObject(Message.class);

        doReturn(receivedMessage).when(mapper).toModel(receivedMessageDTO);
        doReturn(empty()).when(saveMessageService).handle(receivedMessage);

        // WHEN
        MessageDTO result = adapter.create(receivedMessageDTO);

        // THEN
        assertThat(result).isNull();
    }
}
