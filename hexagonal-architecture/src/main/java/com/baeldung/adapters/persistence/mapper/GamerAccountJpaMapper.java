package com.baeldung.adapters.persistence.mapper;

import com.baeldung.adapters.persistence.entity.GamerAccountData;
import com.baeldung.application.domain.GamerAccount;
import org.springframework.stereotype.Component;

@Component
public class GamerAccountJpaMapper {
    public GamerAccountJpaMapper() {
        super();
    }

    public GamerAccountData toJpaEntity(GamerAccount gamerAccount) {
        return GamerAccountData.builder()
                .id(gamerAccount.getId())
                .rankLevel(gamerAccount.getRankLevel())
                .build();
    }

    public GamerAccount toDomain(GamerAccountData gamerAccountData) {
        return new GamerAccount(gamerAccountData.getId(),gamerAccountData.getRankLevel());
    }

}
