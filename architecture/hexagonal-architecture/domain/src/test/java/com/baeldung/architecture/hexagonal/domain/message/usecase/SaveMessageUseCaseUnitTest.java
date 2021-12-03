package com.baeldung.architecture.hexagonal.domain.message.usecase;

import com.baeldung.architecture.hexagonal.domain.message.mapper.MessageMapper;
import com.baeldung.architecture.hexagonal.domain.message.model.Message;
import com.baeldung.architecture.hexagonal.domain.message.spi.persistence.ISaveMessagePort;
import com.baeldung.architecture.hexagonal.domain.message.spi.persistence.ISaveMessagePort.SaveMessageCommand;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.jeasy.random.FieldPredicates;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

class SaveMessageUseCaseUnitTest {

    private final ISaveMessagePort saveMessagePort = mock(ISaveMessagePort.class);
    private final MessageMapper messageMapper = mock(MessageMapper.class);
    private final SaveMessageUseCase saveMessageUseCase = new SaveMessageUseCase(saveMessagePort);

    private final EasyRandomParameters parameters = new EasyRandomParameters().excludeField(FieldPredicates.named("validator"));
    private final EasyRandom generator = new EasyRandom(parameters);

    @BeforeEach
    void setup() {
        saveMessageUseCase.setMapper(messageMapper);
    }

    @Test
    void givenMessage_whenSave_thenFailAndThrowMessageSaveException() {
        // GIVEN
        Message message = generator.nextObject(Message.class);
        SaveMessageCommand command = generator.nextObject(SaveMessageCommand.class);

        doReturn(command).when(messageMapper).MessageToSaveMessageCommand(message);
        doReturn(empty()).when(saveMessagePort).handle(command);

        // WHEN
        assertThrows(MessageSaveException.class, () -> saveMessageUseCase.handle(message));

        // THEN
        then(messageMapper).should().MessageToSaveMessageCommand(message);
        then(saveMessagePort).should().handle(command);

    }

    @Test
    void givenMessage_whenSave_thenSucceedAndReturnMessage() {
        // GIVEN
        Message message = generator.nextObject(Message.class);
        Optional<Message> savedMessage = of(generator.nextObject(Message.class));
        SaveMessageCommand command = generator.nextObject(SaveMessageCommand.class);

        doReturn(command).when(messageMapper).MessageToSaveMessageCommand(message);
        doReturn(savedMessage).when(saveMessagePort).handle(command);

        // WHEN
        Optional<Message> result = saveMessageUseCase.handle(message);

        // THEN
        assertEquals(savedMessage, result);
        then(messageMapper).should().MessageToSaveMessageCommand(message);
        then(saveMessagePort).should().handle(command);

    }
}
