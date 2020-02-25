package com.baeldung.hexagonalarchitecture.useraccount;

import java.util.Optional;

/**
 * @author Fact S Musingarimi
 * 23/2/2020
 * 13:44
 */
public interface UserAccountRepository {
    UserAccount save(UserAccount userAccount);

    Optional<UserAccount> findByUsername(String username);

}
