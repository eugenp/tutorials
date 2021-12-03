package com.baeldung.architecture.hexagonal.domain.message.usecase;

import com.baeldung.architecture.hexagonal.common.annotation.UseCase;
import com.baeldung.architecture.hexagonal.domain.message.api.persistence.IGetAllMessageService;
import com.baeldung.architecture.hexagonal.domain.message.model.Message;
import com.baeldung.architecture.hexagonal.domain.message.spi.persistence.IGetAllMessagePort;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.Set;

@UseCase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class GetAllMessageUseCase implements IGetAllMessageService {

    private final IGetAllMessagePort port;

    @Override
    public Optional<Set<Message>> handle() {
        return Optional.of(port.handle().orElseThrow(MessageGetException::new));
    }
}
