package main.java.com.baeldung.examples.hexagonal;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class InMemoryAccountRepository implements AccountRepository {
    private final Map<UUID, Account> map;

    public InMemoryAccountRepository() {
        this.map = new HashMap<>();
    }

    @Override
    public Optional<Account> findById(UUID id) {
        return Optional.of(map.get(id));
    }

    @Override
    public Account save(Account account) {
        map.put(account.getId(), account);
        return account;
    }
}
