package com.baeldung.infrastructure.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baeldung.domain.ChgRequest;
import com.baeldung.domain.repository.ChgRequestRepository;

@Component
public class CassandraDbChgRequestRepository implements ChgRequestRepository {

    private final SpringDataCassandraChgRequestRepository chgRequestRepository;

    @Autowired
    public CassandraDbChgRequestRepository(SpringDataCassandraChgRequestRepository chgRequestRepository) {
        this.chgRequestRepository = chgRequestRepository;
    }

    @Override
    public List<ChgRequest> findAll() {
        return chgRequestRepository.findAll()
                .stream()
                .map(ChgRequestEntity::toChgRequest)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ChgRequest> findById(UUID id) {
        var maybeInvoiceEntity = chgRequestRepository.findById(id);
        return maybeInvoiceEntity.map(ChgRequestEntity::toChgRequest);
    }

    @Override
    public void save(ChgRequest chgRequest) {
        chgRequestRepository.save(new ChgRequestEntity(chgRequest));
    }
}
