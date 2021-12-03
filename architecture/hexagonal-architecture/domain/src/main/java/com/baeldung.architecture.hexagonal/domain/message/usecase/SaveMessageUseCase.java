package com.baeldung.architecture.hexagonal.domain.message.usecase;

import com.baeldung.architecture.hexagonal.common.annotation.UseCase;
import com.baeldung.architecture.hexagonal.domain.message.api.persistence.ISaveMessageService;
import com.baeldung.architecture.hexagonal.domain.message.mapper.MessageMapper;
import com.baeldung.architecture.hexagonal.domain.message.model.Message;
import com.baeldung.architecture.hexagonal.domain.message.spi.persistence.ISaveMessagePort;
import com.baeldung.architecture.hexagonal.domain.message.spi.persistence.ISaveMessagePort.SaveMessageCommand;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@UseCase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class SaveMessageUseCase implements ISaveMessageService {

    private final ISaveMessagePort port;

    @Setter(onMethod_ = @Autowired)
    private MessageMapper mapper;

    @Override
    public Optional<Message> handle(Message message) {
        SaveMessageCommand command = mapper.MessageToSaveMessageCommand(message);
        return Optional.of(port.handle(command).orElseThrow(MessageSaveException::new));
    }
}

