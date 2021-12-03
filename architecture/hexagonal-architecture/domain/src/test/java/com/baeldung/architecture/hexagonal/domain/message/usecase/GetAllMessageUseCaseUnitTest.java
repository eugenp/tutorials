package com.baeldung.architecture.hexagonal.domain.message.usecase;

import com.baeldung.architecture.hexagonal.domain.message.model.Message;
import com.baeldung.architecture.hexagonal.domain.message.spi.persistence.IGetAllMessagePort;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.jeasy.random.FieldPredicates;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

class GetAllMessageUseCaseUnitTest {

    private final IGetAllMessagePort getAllMessagePort = mock(IGetAllMessagePort.class);
    private final GetAllMessageUseCase getAllMessageUseCase = new GetAllMessageUseCase(getAllMessagePort);

    private final EasyRandomParameters parameters = new EasyRandomParameters().excludeField(FieldPredicates.named("validator"));
    private final EasyRandom generator = new EasyRandom(parameters);

    @Test
    void givenMessage_whenGetAll_thenFailAndThrowMessageGetAllException() {
        // GIVEN
        Message message = generator.nextObject(Message.class);

        doReturn(empty()).when(getAllMessagePort).handle();

        // WHEN
        assertThrows(MessageGetException.class, getAllMessageUseCase::handle);

        // THEN
        then(getAllMessagePort).should().handle();

    }

    @Test
    void givenMessage_whenGetAll_thenSucceedAndReturnMessage() {

        int MESSAGE_NUMBER = 3;
        // GIVEN
        Optional<Set<Message>> savedMessage = of(generator.objects(Message.class, MESSAGE_NUMBER).collect(Collectors.toSet()));

        doReturn(savedMessage).when(getAllMessagePort).handle();

        // WHEN
        Optional<Set<Message>> result = getAllMessageUseCase.handle();

        // THEN
        assertEquals(savedMessage, result);
        then(getAllMessagePort).should().handle();

    }
}
