package com.baeldung.architecture.hexagonal.persistence.postgres.adapter.message;

import com.baeldung.architecture.hexagonal.common.annotation.PersistenceAdapter;
import com.baeldung.architecture.hexagonal.domain.message.model.Message;
import com.baeldung.architecture.hexagonal.domain.message.spi.persistence.IGetAllMessagePort;
import com.baeldung.architecture.hexagonal.persistence.postgres.adapter.message.mapper.IMessagePersistenceMapper;
import com.baeldung.architecture.hexagonal.persistence.postgres.adapter.parent.AbstractGetAllJpaAdapter;
import com.baeldung.architecture.hexagonal.persistence.postgres.repository.IMessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.Set;

@SuppressWarnings("unchecked")
@PersistenceAdapter
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class GetAllMessagePersistenceAdapter extends AbstractGetAllJpaAdapter implements IGetAllMessagePort {

    private final IMessageRepository repository;

    @Setter(onMethod_ = @Autowired)
    private IMessagePersistenceMapper mapper;

    @Override
    public Optional<Set<Message>> handle() {
        return getAll();
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
