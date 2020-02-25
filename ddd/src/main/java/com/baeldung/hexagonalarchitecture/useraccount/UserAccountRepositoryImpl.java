package com.baeldung.hexagonalarchitecture.useraccount;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Fact S Musingarimi
 * 23/2/2020
 * 17:22
 */
@Service
class UserAccountRepositoryImpl implements UserAccountRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserAccountRepositoryImpl.class);
    private final UserAccountEntityRepository userAccountEntityRepository;

    UserAccountRepositoryImpl(UserAccountEntityRepository userAccountEntityRepository) {
        this.userAccountEntityRepository = userAccountEntityRepository;
    }

    @Override
    public UserAccount save(UserAccount userAccount) {
        LOGGER.info("Saving user account: {}", userAccount);
        UserAccountEntity userAccountEntity = UserAccountEntity.of(userAccount);
        userAccountEntity = userAccountEntityRepository.save(userAccountEntity);
        return userAccountEntity.getUserAccount();
    }

    @Override
    public Optional<UserAccount> findByUsername(String username) {
        LOGGER.info("Get user account by username: {}", username);
        Optional<UserAccountEntity> optionalUserAccountEntity = userAccountEntityRepository.findByUsername(username);
        return Optional.ofNullable(optionalUserAccountEntity.map(userAccountEntity -> userAccountEntity.getUserAccount())
                .orElse(null));
    }
}
