package com.baeldung.hexagonal.banking.output.adapter;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class AccountMapperFactory {

    final static Map<String, Supplier<AccountMapper>> map = new HashMap<>();
    static {
        map.put("Commercial", CommercialAccountMapper::new);
        map.put("Consumer", ConsumerAccountMapper::new);
    }

    public AccountMapper getMapper(String accountType) {
        Supplier<AccountMapper> mapper = map.get(accountType);
        if (mapper != null) {
            return mapper.get();
        }
        throw new IllegalArgumentException("No such type " + mapper);
    }
}
