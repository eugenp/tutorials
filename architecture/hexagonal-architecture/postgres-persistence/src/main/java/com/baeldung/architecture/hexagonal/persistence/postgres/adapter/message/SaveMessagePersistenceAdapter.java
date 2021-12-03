package com.baeldung.architecture.hexagonal.persistence.postgres.adapter.message;

import com.baeldung.architecture.hexagonal.common.annotation.PersistenceAdapter;
import com.baeldung.architecture.hexagonal.domain.message.model.Message;
import com.baeldung.architecture.hexagonal.domain.message.spi.persistence.ISaveMessagePort;
import com.baeldung.architecture.hexagonal.persistence.postgres.adapter.message.mapper.IMessagePersistenceMapper;
import com.baeldung.architecture.hexagonal.persistence.postgres.adapter.parent.AbstractSaveJpaAdapter;
import com.baeldung.architecture.hexagonal.persistence.postgres.entity.MessageEntity;
import com.baeldung.architecture.hexagonal.persistence.postgres.repository.IMessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@SuppressWarnings("unchecked")
@PersistenceAdapter
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class SaveMessagePersistenceAdapter extends AbstractSaveJpaAdapter implements ISaveMessagePort {

    private final IMessageRepository repository;

    @Setter(onMethod_ = @Autowired)
    private IMessagePersistenceMapper mapper;

    @Override
    public Optional<Message> handle(SaveMessageCommand command) {
        MessageEntity entity = mapper.saveMessageCommandToEntity(command);
        return save(entity);
    }

    @Override
    protected IMessageRepository getRepository() {
        return repository;
    }

    @Override
    protected IMessagePersistenceMapper getMapper() {
        return mapper;
    }
}
