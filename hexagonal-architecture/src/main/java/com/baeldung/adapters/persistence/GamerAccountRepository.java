package com.baeldung.adapters.persistence;

import com.baeldung.adapters.persistence.mapper.GamerAccountJpaMapper;
import com.baeldung.application.domain.GamerAccount;
import com.baeldung.application.port.output.GetAccountPort;
import com.baeldung.application.port.output.SaveAccountPort;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
public class GamerAccountRepository implements GetAccountPort, SaveAccountPort {

    private final GamerAccountJpaMapper gamerAccountJpaMapper;
    private final SpringJpaGamerAccountRepository repository;

    public GamerAccountRepository(GamerAccountJpaMapper gamerAccountJpaMapper, SpringJpaGamerAccountRepository repository) {
        this.gamerAccountJpaMapper = gamerAccountJpaMapper;
        this.repository = repository;
    }


    @Override
    public GamerAccount load(Long id) {

        return gamerAccountJpaMapper.toDomain(repository.findById(id).orElseThrow(NoSuchElementException::new));

    }

    @Override
    public void save(GamerAccount gamerAccount) {
        repository.save(gamerAccountJpaMapper.toJpaEntity(gamerAccount));
    }
}
